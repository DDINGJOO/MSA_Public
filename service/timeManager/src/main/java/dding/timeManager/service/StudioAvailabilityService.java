package dding.timeManager.service;

import dding.timeManager.entity.Studio.StudioReservationTimeSlot;
import dding.timeManager.entity.Studio.StudioTimeSlot;
import dding.timeManager.repository.StudioReservationTimeSlotRepository;
import dding.timeManager.repository.StudioTimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudioAvailabilityService {

    private final StudioTimeSlotRepository timeSlotRepository;
    private final StudioReservationTimeSlotRepository reservationSlotRepository;
    private final HolidayService holidayService; // 공휴일 여부 판단 유틸

    /**
     * 예약 가능한 시간 리스트 반환
     */
    public List<Integer> getAvailableReservationHours(String studioId, LocalDate date) {
        int dayOfWeek = date.getDayOfWeek().getValue(); // 1 = 월, ..., 7 = 일
        boolean isHoliday = holidayService.isHoliday(date); // 공휴일 여부

        // 1. 운영 가능한 시간대 조회
        List<Integer> availableHours = timeSlotRepository.findByStudioIdAndDayOfWeekAndIsHolidayAndAvailableTrue(
                        studioId, dayOfWeek, isHoliday
                ).stream()
                .map(StudioTimeSlot::getHour)
                .collect(Collectors.toList());

        // 2. 이미 예약된 시간대 조회
        List<Integer> reservedHours = reservationSlotRepository.findByStudioIdAndDate(
                        studioId, date
                ).stream()
                .map(StudioReservationTimeSlot::getHour)
                .collect(Collectors.toList());

        // 3. 예약 가능 시간 = 운영 시간 - 예약된 시간
        return availableHours.stream()
                .filter(hour -> !reservedHours.contains(hour))
                .sorted()
                .collect(Collectors.toList());
    }
}
