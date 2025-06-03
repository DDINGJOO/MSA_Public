package dding.timeManager.service.Studio;

import dding.timeManager.config.RecurrencePattern;
import dding.timeManager.dto.request.Stodio.StudioWeeksDto;
import dding.timeManager.entity.BandRoom.BandRoomWeek;
import dding.timeManager.entity.Studio.StudioWeek;
import dding.timeManager.repository.BandRoomWeekRepository;
import dding.timeManager.repository.StudioWeekRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.annotation.Rollback;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
@Rollback(false)
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class StudioWeekServiceTest {

    @Mock private StudioWeekRepository studioWeekRepository;
    @Mock private BandRoomWeekRepository bandRoomWeekRepository;

    @InjectMocks private StudioWeekService studioWeekService;

    private StudioWeeksDto request;

    @BeforeEach
    void setUp() {
        request = new StudioWeeksDto();
        request.setDayOfWeek(1); // 월요일
        request.setStartTime(LocalTime.of(9, 0));
        request.setEndTime(LocalTime.of(12, 0));
        request.setRecurrencePattern(RecurrencePattern.WEEKLY);
        request.setIsOddWeek(null); // 주차 상관없이 항상 운영
        request.setClosed(false);
        request.setSpecialPrice(10000);
    }

    @Test @DisplayName("[StudioWeekService] saveStudioWeek - 선택된 시간만 열림, 나머지는 닫힘 + overridden=true")
    void saveStudioWeek_selectedHoursOpen_restClosed() {
        // given
        String studioId = "studioUnitTest";
        StudioWeeksDto request = new StudioWeeksDto();
        request.setDayOfWeek(1); // 월요일
        request.setStartTime(LocalTime.of(9, 0));
        request.setEndTime(LocalTime.of(12, 0)); // 9~11 열림
        request.setRecurrencePattern(RecurrencePattern.WEEKLY);
        request.setIsOddWeek(null);
        request.setClosed(false);
        request.setSpecialPrice(10000);


// when
        studioWeekService.saveStudioWeek(studioId, request);

// then
        ArgumentCaptor<List<StudioWeek>> captor = ArgumentCaptor.forClass(List.class);
        verify(studioWeekRepository).saveAll(captor.capture());

        List<StudioWeek> saved = captor.getValue();

// 검증
        assertThat(saved).isNotNull();
        assertThat(saved).extracting(StudioWeek::getStudioId).containsOnly(studioId);

        List<Integer> expectedOpenHours = List.of(9, 10, 11);

        for (StudioWeek slot : saved) {
            assertThat(slot.getDayOfWeek()).isEqualTo(1); // 월요일
            assertThat(slot.getOverridden()).isTrue();

            if (expectedOpenHours.contains(slot.getHour())) {
                assertThat(slot.isClosed()).isFalse();
                assertThat(slot.getSpecialPrice()).isEqualTo(10000);
            } else {
                assertThat(slot.isClosed()).isTrue();
                assertThat(slot.getSpecialPrice()).isNull();
            }
        }

        assertThat(saved).hasSize(3); // 월요일 전체 시간대 커버
    }

    @Test
    @DisplayName("[StudioWeekService] initStudioWeeksFromBandRoom - 정상 초기화")
    void initStudioWeeksFromBandRoom_success() {
        // given
        String bandRoomId = "BR001";
        String studioId = "STU001";

        BandRoomWeek bw1 = BandRoomWeek.builder()
                .dayOfWeek(1)
                .hour(10)
                .isClosed(false)
                .recurrencePattern(RecurrencePattern.WEEKLY)
                .isOddWeek(false)
                .specialPrice(10000)
                .build();

        BandRoomWeek bw2 = BandRoomWeek.builder()
                .dayOfWeek(1)
                .hour(11)
                .isClosed(false)
                .recurrencePattern(RecurrencePattern.WEEKLY)
                .isOddWeek(false)
                .specialPrice(null)
                .build();

        when(bandRoomWeekRepository.findAllByBandRoomId(bandRoomId))
                .thenReturn(List.of(bw1, bw2));

        // when
        studioWeekService.initStudioWeeksFromBandRoom(bandRoomId, studioId);

        // then
        ArgumentCaptor<List<StudioWeek>> captor = ArgumentCaptor.forClass(List.class);
        verify(studioWeekRepository).saveAll(captor.capture());

        List<StudioWeek> saved = captor.getValue();
        assertThat(saved).hasSize(2);

        StudioWeek slot1 = saved.get(0);
        assertThat(slot1.getStudioId()).isEqualTo(studioId);
        assertThat(slot1.getDayOfWeek()).isEqualTo(1);
        assertThat(slot1.getHour()).isEqualTo(10);
        assertThat(slot1.isClosed()).isFalse();
        assertThat(slot1.getRecurrencePattern()).isEqualTo(RecurrencePattern.WEEKLY);
        assertThat(slot1.getSpecialPrice()).isEqualTo(10000);
        assertThat(slot1.getOverridden()).isFalse();

        StudioWeek slot2 = saved.get(1);
        assertThat(slot2.getHour()).isEqualTo(11);
        assertThat(slot2.getSpecialPrice()).isNull();
    }

    @Test
    @DisplayName("[StudioWeekService] 사용자 커스터마이징 입력 → 기존 삭제 + isOverridden = true 저장")
    void upDateStudioWeeks_userOverride_success() {
        // given
        String studioId = "STU_CUSTOM";
        String bandRoomId = "BAD_CUSTOM";
        StudioWeeksDto req1 = new StudioWeeksDto();
        req1.setDayOfWeek(1); // 월요일
        req1.setStartTime(LocalTime.of(9, 0));
        req1.setEndTime(LocalTime.of(12, 0)); // 9~11시
        req1.setRecurrencePattern(RecurrencePattern.WEEKLY);
        req1.setIsOddWeek(false);
        req1.setClosed(false);
        req1.setSpecialPrice(12000);

        StudioWeeksDto req2 = new StudioWeeksDto();
        req2.setDayOfWeek(2); // 화요일
        req2.setStartTime(LocalTime.of(14, 0));
        req2.setEndTime(LocalTime.of(16, 0)); // 14~15시
        req2.setRecurrencePattern(RecurrencePattern.BIWEEKLY);
        req2.setIsOddWeek(true);
        req2.setClosed(false);
        req2.setSpecialPrice(15000);

        when(studioWeekRepository.existsByStudioId(studioId)).thenReturn(true);

// bandRoom에서 연동되는 slot 모의 데이터
        BandRoomWeek band1 = BandRoomWeek.builder()
                .dayOfWeek(0).hour(10).isClosed(false)
                .recurrencePattern(RecurrencePattern.WEEKLY)
                .isOddWeek(false).specialPrice(null)
                .build();
        when(bandRoomWeekRepository.findAllByBandRoomId(bandRoomId)).thenReturn(List.of(band1));

// when
        studioWeekService.upDateStudioWeeks(studioId, bandRoomId, List.of(req1, req2));

// then
        verify(studioWeekRepository).deleteAllByStudioId(studioId);

        ArgumentCaptor<List<StudioWeek>> captor = ArgumentCaptor.forClass(List.class);
        verify(studioWeekRepository, times(3)).saveAll(captor.capture()); // bandRoom .req1, req2 굳!

        List<List<StudioWeek>> allSaved = captor.getAllValues();

        //밴드룸은 목객체 설정때문에 인잇 테스트에서 확인완료
        // 두 번째, 세 번째는 사용자 입력
        List<StudioWeek> userSaved = new ArrayList<>();
        userSaved.addAll(allSaved.get(1));
        userSaved.addAll(allSaved.get(2));

        assertThat(userSaved).hasSize(5);
        for (StudioWeek sw : userSaved) {
            assertThat(sw.getStudioId()).isEqualTo(studioId);
            assertThat(sw.getOverridden()).isTrue();
            assertThat(sw.isClosed()).isFalse();
            assertThat(sw.getSpecialPrice()).isIn(12000, 15000);
        }
    }
}
