package dding.timeManager.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class HolidayService {

    private static final Set<LocalDate> HOLIDAYS = Set.of(
            LocalDate.of(2025, 1, 1),   // 신정
            LocalDate.of(2025, 3, 1),   // 삼일절
            LocalDate.of(2025, 5, 5),   // 어린이날
            LocalDate.of(2025, 6, 6),   // 현충일
            LocalDate.of(2025, 8, 15),  // 광복절
            LocalDate.of(2025, 10, 3),  // 개천절
            LocalDate.of(2025, 12, 25)  // 크리스마스
            // 필요 시 음력 처리 또는 추가 가능
    );

    public boolean isHoliday(LocalDate date) {
        return HOLIDAYS.contains(date);
    }
}
