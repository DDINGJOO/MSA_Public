package dding.timeManager.service.Studio;

import dding.timeManager.config.RecurrencePattern;
import dding.timeManager.entity.BandRoom.BandRoomWeek;
import dding.timeManager.entity.Studio.StudioWeek;
import dding.timeManager.repository.BandRoomWeekRepository;
import dding.timeManager.repository.StudioTimeSlotRepository;
import dding.timeManager.repository.StudioWeekRepository;
import dding.timeManager.service.Studio.StudioAvailabilityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
public class StudioAvailabilityBiweeklyTest {

    @Mock private StudioTimeSlotRepository studioTimeSlotRepository;
    @Mock private StudioWeekRepository studioWeekRepository;
    @Mock private BandRoomWeekRepository bandRoomWeekRepository;

    @InjectMocks private StudioAvailabilityService studioAvailabilityService;

    @Test
    @DisplayName("[StudioAvailability] studioId 존재하지 않음 + bandRoomWeek 있음(홀수주 일치) → 열림")
    void isStudioOpen_fallbackToBandRoomWeek_oddWeek_match() {
        LocalDate date = LocalDate.of(2025, 5, 4); // 19주차 → 홀수주
        LocalTime time = LocalTime.of(11, 0);
        int dayOfWeek = date.getDayOfWeek().getValue() % 7;
        int hour = time.getHour();

        // 스튜디오 정보 없음
        lenient().when(studioTimeSlotRepository.existsByStudioIdAndDateAndHour("STU_X", date, hour)).thenReturn(false);
        lenient().when(studioWeekRepository.existsByStudioIdAndDayOfWeekAndHour("STU_X", dayOfWeek, hour)).thenReturn(false);

        BandRoomWeek slot = mock(BandRoomWeek.class);
        when(slot.isClosed()).thenReturn(false);
        when(slot.getRecurrencePattern()).thenReturn(RecurrencePattern.BIWEEKLY);
        when(slot.getIsOddWeek()).thenReturn(true); // 홀수주 운영

        when(bandRoomWeekRepository.findByBandRoomIdAndDayOfWeekAndHour("BR100", dayOfWeek, hour))
                .thenReturn(slot);

        boolean result = studioAvailabilityService.isStudioOpen("BR100", "STU_X", date, time);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("[StudioAvailability] studioId 존재하지 않음 + bandRoomWeek 있음(홀수주 불일치) → 닫힘")
    void isStudioOpen_fallbackToBandRoomWeek_oddWeek_mismatch() {
        LocalDate date = LocalDate.of(2025, 5, 11); // 20주차 → 짝수주
        LocalTime time = LocalTime.of(11, 0);
        int dayOfWeek = date.getDayOfWeek().getValue() % 7;
        int hour = time.getHour();

        // 스튜디오 정보 없음

        lenient().when(studioTimeSlotRepository.existsByStudioIdAndDateAndHour("STU_X", date, hour)).thenReturn(false);
        lenient().when(studioWeekRepository.existsByStudioIdAndDayOfWeekAndHour("STU_X", dayOfWeek, hour)).thenReturn(false);

        BandRoomWeek slot = mock(BandRoomWeek.class);
        when(slot.isClosed()).thenReturn(false);
        when(slot.getRecurrencePattern()).thenReturn(RecurrencePattern.BIWEEKLY);
        when(slot.getIsOddWeek()).thenReturn(true); // 홀수주 운영인데 현재는 짝수주

        when(bandRoomWeekRepository.findByBandRoomIdAndDayOfWeekAndHour("BR101", dayOfWeek, hour))
                .thenReturn(slot);

        boolean result = studioAvailabilityService.isStudioOpen("BR101", "STU_X", date, time);
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("[StudioAvailability] studioId 존재하지 않음 + bandRoomWeek null → 닫힘")
    void isStudioOpen_fallbackToBandRoomWeek_null() {
        LocalDate date = LocalDate.of(2025, 5, 10);
        LocalTime time = LocalTime.of(12, 0);
        int dayOfWeek = date.getDayOfWeek().getValue() % 7;
        int hour = time.getHour();

        // 모든 정보 없음

        lenient().when(studioTimeSlotRepository.existsByStudioIdAndDateAndHour("STU_X", date, hour)).thenReturn(false);
        lenient().when(studioWeekRepository.existsByStudioIdAndDayOfWeekAndHour("STU_X", dayOfWeek, hour)).thenReturn(false);
        when(bandRoomWeekRepository.findByBandRoomIdAndDayOfWeekAndHour("BR404", dayOfWeek, hour)).thenReturn(null);

        boolean result = studioAvailabilityService.isStudioOpen("BR404", "STU_NONE", date, time);
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("[StudioAvailability] 스튜디오에 커스텀 슬롯 존재(isClosed=false) → 열림")
    void isStudioOpen_studioSlot_open() { LocalDate date = LocalDate.of(2025, 5, 7); // 수요일
        LocalTime time = LocalTime.of(13, 0); int dayOfWeek = date.getDayOfWeek().getValue() % 7; int hour = time.getHour();
        when(studioTimeSlotRepository.existsByStudioIdAndDateAndHour("STU_Y", date, hour)).thenReturn(false);

        StudioWeek studioSlot = mock(StudioWeek.class);
        when(studioSlot.isClosed()).thenReturn(false);

        when(studioWeekRepository.existsByStudioIdAndDayOfWeekAndHour("STU_Y", dayOfWeek, hour)).thenReturn(true);
        when(studioWeekRepository.findByStudioIdAndDayOfWeekAndHour("STU_Y", dayOfWeek, hour)).thenReturn(studioSlot);

        boolean result = studioAvailabilityService.isStudioOpen("BR_Y", "STU_Y", date, time);
        assertThat(result).isTrue();
    }

    @Test @DisplayName("[StudioAvailability] 스튜디오에 커스텀 슬롯 존재(isClosed=true) → 닫힘") void isStudioOpen_studioSlot_closed() { LocalDate date = LocalDate.of(2025, 5, 8); // 목요일


        LocalTime time = LocalTime.of(15, 0); int dayOfWeek = date.getDayOfWeek().getValue() % 7; int hour = time.getHour();

        when(studioTimeSlotRepository.existsByStudioIdAndDateAndHour("STU_Z", date, hour)).thenReturn(false);

        StudioWeek studioSlot = mock(StudioWeek.class);
        when(studioSlot.isClosed()).thenReturn(true);

        when(studioWeekRepository.existsByStudioIdAndDayOfWeekAndHour("STU_Z", dayOfWeek, hour)).thenReturn(true);
        when(studioWeekRepository.findByStudioIdAndDayOfWeekAndHour("STU_Z", dayOfWeek, hour)).thenReturn(studioSlot);

        boolean result = studioAvailabilityService.isStudioOpen("BR_Z", "STU_Z", date, time);
        assertThat(result).isFalse();
    }
}
