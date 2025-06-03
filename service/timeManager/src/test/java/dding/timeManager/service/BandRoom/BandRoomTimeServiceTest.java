package dding.timeManager.service.BandRoom;

import dding.timeManager.dto.request.BandRoom.BandRoomWeekRequest;
import dding.timeManager.entity.BandRoom.BandRoomWeek;
import dding.timeManager.repository.BandRoomWeekRepository;
import dding.timeManager.service.Studio.StudioAvailabilityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BandRoomTimeServiceTest {

    @Mock private BandRoomWeekRepository bandRoomWeekRepository;
    @Mock private StudioAvailabilityService studioAvailabilityService;

    @InjectMocks private BandRoomWeekService bandRoomWeekService;

    private BandRoomWeekRequest requestNormal;
    private BandRoomWeekRequest requestCrossDay;
    private BandRoomWeekRequest requestEndAt24;

    @BeforeEach
    void setUp() {
        requestNormal = mock(BandRoomWeekRequest.class, withSettings().lenient());
        when(requestNormal.getDayOfWeek()).thenReturn(3);
        when(requestNormal.getStartTime()).thenReturn(LocalTime.of(10, 0));
        when(requestNormal.getEndTime()).thenReturn(LocalTime.of(13, 0));

        requestCrossDay = mock(BandRoomWeekRequest.class, withSettings().lenient());
        when(requestCrossDay.getDayOfWeek()).thenReturn(5);
        when(requestCrossDay.getStartTime()).thenReturn(LocalTime.of(22, 0));
        when(requestCrossDay.getEndTime()).thenReturn(LocalTime.of(2, 0));

        requestEndAt24 = mock(BandRoomWeekRequest.class, withSettings().lenient());
        when(requestEndAt24.getDayOfWeek()).thenReturn(2);
        when(requestEndAt24.getStartTime()).thenReturn(LocalTime.of(21, 0));
        when(requestEndAt24.getEndTime()).thenReturn(LocalTime.MIDNIGHT); // 24:00
    }

    @Test
    @DisplayName("[BandRoomWeekService] saveBandRoomWeek - 일반 구간 저장 확인")
    void saveBandRoomWeek_normalTimeRange() {
        bandRoomWeekService.saveBandRoomWeek("BR100", requestNormal);

        ArgumentCaptor<List<BandRoomWeek>> captor = ArgumentCaptor.forClass(List.class);
        verify(bandRoomWeekRepository).saveAll(captor.capture());

        List<BandRoomWeek> saved = captor.getValue();
        assertThat(saved).hasSize(3);
        assertThat(saved).extracting(BandRoomWeek::getHour).containsExactly(10, 11, 12);
    }

    @Test
    @DisplayName("[BandRoomWeekService] saveBandRoomWeek - cross-day(22~2) 구간 저장 확인")
    void saveBandRoomWeek_crossDay() {
        bandRoomWeekService.saveBandRoomWeek("BR101", requestCrossDay);

        ArgumentCaptor<List<BandRoomWeek>> captor = ArgumentCaptor.forClass(List.class);
        verify(bandRoomWeekRepository).saveAll(captor.capture());

        List<BandRoomWeek> saved = captor.getValue();
        assertThat(saved).hasSize(4);
        assertThat(saved).extracting(BandRoomWeek::getHour).containsExactly(22, 23, 0, 1);
        assertThat(saved.get(0).getDayOfWeek()).isEqualTo(5);
        assertThat(saved.get(2).getDayOfWeek()).isEqualTo(6);
    }

    @Test
    @DisplayName("[BandRoomWeekService] saveBandRoomWeek - 종료 시각 24시인 경우 23시까지 저장 확인")
    void saveBandRoomWeek_endsAt24() {
        bandRoomWeekService.saveBandRoomWeek("BR102", requestEndAt24);

        ArgumentCaptor<List<BandRoomWeek>> captor = ArgumentCaptor.forClass(List.class);
        verify(bandRoomWeekRepository).saveAll(captor.capture());

        List<BandRoomWeek> saved = captor.getValue();
        assertThat(saved).hasSize(3);
        assertThat(saved).extracting(BandRoomWeek::getHour).containsExactly(21, 22, 23);
    }

    @Test
    @DisplayName("[BandRoomWeekService] saveBandRoomWeeks - 요일별 삭제 + 저장 확인")
    void saveBandRoomWeeks_deletesThenSaves() {
        bandRoomWeekService.saveBandRoomWeeks("BR200", List.of(requestNormal, requestCrossDay));

        verify(bandRoomWeekRepository).deleteByBandRoomIdAndDayOfWeek("BR200", 3);
        verify(bandRoomWeekRepository).deleteByBandRoomIdAndDayOfWeek("BR200", 5);
        verify(bandRoomWeekRepository, times(2)).saveAll(anyList());
    }

    @Test
    @DisplayName("[BandRoomWeekService] isOpenCheck - 존재하고 열림 상태")
    void isOpenCheck_true() {
        LocalDate date = LocalDate.of(2025, 4, 25);
        LocalTime time = LocalTime.of(11, 0);

        BandRoomWeek mockSlot = mock(BandRoomWeek.class);
        when(mockSlot.isClosed()).thenReturn(false);
        when(bandRoomWeekRepository.findByBandRoomIdAndDayOfWeekAndHour("BR300", 5, 11)).thenReturn(mockSlot);

        boolean result = bandRoomWeekService.isOpenCheck("BR300", date, time);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("[BandRoomWeekService] isOpenCheck - 존재하지만 닫힘 상태")
    void isOpenCheck_false_closed() {
        LocalDate date = LocalDate.of(2025, 4, 25);
        LocalTime time = LocalTime.of(13, 0);

        BandRoomWeek mockSlot = mock(BandRoomWeek.class);
        when(mockSlot.isClosed()).thenReturn(true);
        when(bandRoomWeekRepository.findByBandRoomIdAndDayOfWeekAndHour("BR300", 5, 13)).thenReturn(mockSlot);

        boolean result = bandRoomWeekService.isOpenCheck("BR300", date, time);
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("[BandRoomWeekService] isOpenCheck - 존재하지 않는 경우")
    void isOpenCheck_false_notFound() {
        LocalDate date = LocalDate.of(2025, 4, 25);
        LocalTime time = LocalTime.of(15, 0);

        when(bandRoomWeekRepository.findByBandRoomIdAndDayOfWeekAndHour("BR300", 5, 15)).thenReturn(null);

        boolean result = bandRoomWeekService.isOpenCheck("BR300", date, time);
        assertThat(result).isFalse();
    }
}
