package dding.timeManager.service.Studio;

import dding.timeManager.config.RecurrencePattern;
import dding.timeManager.dto.response.Avaiable.AvailableHourResponse;
import dding.timeManager.entity.BandRoom.BandRoomWeek;
import dding.timeManager.entity.HolidatSlot.HolidaySlot;
import dding.timeManager.entity.Studio.StudioTimeSlot;
import dding.timeManager.entity.Studio.StudioWeek;
import dding.timeManager.repository.BandRoomWeekRepository;
import dding.timeManager.repository.HolidaySlotRepository;
import dding.timeManager.repository.StudioTimeSlotRepository;
import dding.timeManager.repository.StudioWeekRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class StudioAvailabilityService {


    private final StudioTimeSlotRepository studioTimeSlotRepository;
    private final StudioWeekRepository studioWeekRepository;
    private final BandRoomWeekRepository bandRoomWeekRepository;

    /**
     * 해당 스튜디오가 주어진 날짜, 시간에 오픈 상태인지 확인
     */
    public boolean isStudioOpen(String bandRoomId, String studioId, LocalDate date, LocalTime time) {


        int dayOfWeek = date.getDayOfWeek().getValue() % 7; // (월=1 ~ 일=7) ➔ (일=0 ~ 토=6)
        int hour = (time != null) ? time.getHour() : -1;

        // 1. 특정 날짜 스튜디오 슬롯 우선 확인
        if (studioTimeSlotRepository.existsByStudioIdAndDateAndHour(studioId, date, hour)) {
            return !studioTimeSlotRepository.findByStudioIdAndDateAndHour(studioId, date, hour).isClosed();
        }

        // 2. 공휴일 슬롯 확인
//        if (holidaySlotRepository.existsByStudioIdAndDateAndHour(studioId, date, hour)) {
//            return !holidaySlotRepository.findByStudioIdAndDateAndHour(studioId, date, hour).isClosed();
//        }

        // 3. 스튜디오 주간 기본 시간 확인
        if (studioWeekRepository.existsByStudioIdAndDayOfWeekAndHour(studioId, dayOfWeek, hour)) {
            return !studioWeekRepository.findByStudioIdAndDayOfWeekAndHour(studioId, dayOfWeek, hour).isClosed();
        }

        // 4. 합주실 기본 시간 확인
        BandRoomWeek bandRoomWeek = bandRoomWeekRepository.findByBandRoomIdAndDayOfWeekAndHour(bandRoomId, dayOfWeek, hour);

        if (bandRoomWeek != null && !bandRoomWeek.isClosed()) {

            if (bandRoomWeek.getRecurrencePattern() == RecurrencePattern.BIWEEKLY) {
                int week = date.get(WeekFields.of(Locale.KOREA).weekOfWeekBasedYear());
                boolean isOddWeek = week % 2 == 1;

                if (bandRoomWeek.getIsOddWeek() != null && !isOddWeek == bandRoomWeek.getIsOddWeek()) {
                    return false;
                }
            }

            return true;
        }
        return false;
    }

    /**
     * 해당 스튜디오가 주어진 날짜에 예약 가능한 시간 리스트 반환
     */
    public List<AvailableHourResponse> findAvailableHours(String bandRoomId, String studioId, LocalDate date) {
        List<AvailableHourResponse> availableHours = new ArrayList<>();

        for (int hour = 0; hour < 24; hour++) {
            LocalTime time = LocalTime.of(hour, 0);
            if (isStudioOpen(bandRoomId,studioId, date, time)) {
                // 가격도 조회해서 넘겨줄 수도 있음
                Integer specialPrice = findSpecialPrice(studioId, date, hour);
                availableHours.add(new AvailableHourResponse(hour, specialPrice));
            }
        }
        return availableHours;
    }

    /**
     * 주어진 날짜, 시간에 대한 특별가격 조회
     */
    private Integer findSpecialPrice(String studioId, LocalDate date, int hour) {
        // 1. 특별날짜 설정
        StudioTimeSlot timeSlot = studioTimeSlotRepository.findByStudioIdAndDateAndHour(studioId, date, hour);
        if (timeSlot != null && timeSlot.getSpecialPrice() != null) {
            return timeSlot.getSpecialPrice();
        }

        // 2. 공휴일 설정
//        HolidaySlot holidaySlot = holidaySlotRepository.findByStudioIdAndDateAndHour(studioId, date, hour);
//        if (holidaySlot != null && holidaySlot.getSpecialPrice() != null) {
//            return holidaySlot.getSpecialPrice();
//        }

        // 3. 주간 기본 설정
        StudioWeek weekSlot = studioWeekRepository.findByStudioIdAndDayOfWeekAndHour(studioId, date.getDayOfWeek().getValue() % 7, hour);
        if (weekSlot != null && weekSlot.getSpecialPrice() != null) {
            return weekSlot.getSpecialPrice();
        }

        return null; // 특별 가격 없으면 null
    }
}
