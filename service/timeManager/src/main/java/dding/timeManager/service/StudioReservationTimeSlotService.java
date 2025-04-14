package dding.timeManager.service;

import dding.timeManager.entity.Studio.StudioReservationTimeSlot;
import dding.timeManager.repository.StudioReservationTimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudioReservationTimeSlotService {

    private final StudioReservationTimeSlotRepository slotRepository;

    /**
     * 예약 시간대를 1시간 단위로 저장
     */
    public void saveReservationSlots(String studioId, LocalDate date, int startHour, int duration, String reservationId) {
        List<StudioReservationTimeSlot> slots = new ArrayList<>();
        for (int hour = startHour; hour < startHour + duration; hour++) {
            slots.add(StudioReservationTimeSlot.builder()
                    .studioId(studioId)
                    .date(date)
                    .hour(hour)
                    .reservationId(reservationId)
                    .createdAt(LocalDateTime.now())
                    .build());
        }
        slotRepository.saveAll(slots);
    }

    /**
     * 특정 날짜의 스튜디오 예약 시간대 조회
     */
    public List<Integer> getReservedHours(String studioId, LocalDate date) {
        return slotRepository.findByStudioIdAndDate(studioId, date)
                .stream()
                .map(StudioReservationTimeSlot::getHour)
                .collect(Collectors.toList());
    }
}

