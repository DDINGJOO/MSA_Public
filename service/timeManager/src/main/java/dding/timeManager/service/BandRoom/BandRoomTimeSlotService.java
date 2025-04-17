package dding.timeManager.service.BandRoom;
import dding.timeManager.config.RecurrencePattern;
import dding.timeManager.dto.request.BandRoomTimeSlotRequest;
import dding.timeManager.dto.response.BandRoomTimeSlotResponse;
import dding.timeManager.entity.BandRoom.BandRoomTimeSlot;
import dding.timeManager.repository.BandRoomTimeSlotRepository;
import io.micrometer.common.lang.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BandRoomTimeSlotService {

    private final BandRoomTimeSlotRepository bandRoomTimeSlotRepository;

    @Transactional
    public String saveAll(String bandRoomId, List<BandRoomTimeSlotRequest> requests) {
        List<BandRoomTimeSlot> slots = new ArrayList<>();

        for (BandRoomTimeSlotRequest request : requests) {
            slots.addAll(generateTimeSlots(bandRoomId, request));
        }

        bandRoomTimeSlotRepository.saveAll(slots);
        return bandRoomId;
    }

    @Transactional
    public void updateTimeSlots(String bandRoomId, List<BandRoomTimeSlotRequest> requests) {
        bandRoomTimeSlotRepository.deleteAllByBandRoomId(bandRoomId);
        saveAll(bandRoomId, requests);
    }

    @Transactional(readOnly = true)
    public List<BandRoomTimeSlotResponse> getByBandRoom(String bandRoomId) {
        return bandRoomTimeSlotRepository.findByBandRoomId(bandRoomId).stream()
                .map(slot -> BandRoomTimeSlotResponse.builder()
                        .id(slot.getId().toString())
                        .dayOfWeek(slot.getDayOfWeek())
                        .startTime(slot.getStartTime().toString())
                        .endTime(slot.getEndTime().toString())
                        .recurrencePattern(slot.getRecurrencePattern().name())
                        .isClosed(slot.getIsClosed())
                        .isOddWeek(slot.getIsOddWeek())
                        .build())
                .toList();
    }

    @Transactional
    public void delete(Long id) {
        bandRoomTimeSlotRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll(String bandRoomId) {
        bandRoomTimeSlotRepository.deleteAllByBandRoomId(bandRoomId);
    }

    private List<BandRoomTimeSlot> generateTimeSlots(String bandRoomId, BandRoomTimeSlotRequest request) {
        List<BandRoomTimeSlot> slots = new ArrayList<>();

        int dayOfWeek = request.getDayOfWeek();
        LocalTime startTime = LocalTime.parse(request.getStartTime());
        LocalTime endTime;

        // 24:00 처리
        if ("24:00".equals(request.getEndTime())) {
            endTime = LocalTime.of(0, 0);
        } else {
            endTime = LocalTime.parse(request.getEndTime());
        }

        boolean crossesMidnight = endTime.isBefore(startTime) || endTime.equals(LocalTime.MIDNIGHT);

        // 자정 넘어가는 경우: 오늘 + 내일 분리해서 슬롯 생성
        if (crossesMidnight) {
            for (int hour = startTime.getHour(); hour < 24; hour++) {
                slots.add(buildSlot(bandRoomId, dayOfWeek, hour, request));
            }
            int nextDay = (dayOfWeek % 7) + 1; // 일요일 넘으면 월요일로
            for (int hour = 0; hour < endTime.getHour(); hour++) {
                slots.add(buildSlot(bandRoomId, nextDay, hour, request));
            }
        } else {
            // 같은 날 내에서만
            for (int hour = startTime.getHour(); hour < endTime.getHour(); hour++) {
                slots.add(buildSlot(bandRoomId, dayOfWeek, hour, request));
            }
        }

        return slots;
    }

    private BandRoomTimeSlot buildSlot(String bandRoomId, int dayOfWeek, int hour, BandRoomTimeSlotRequest request) {
        return BandRoomTimeSlot.builder()
                .bandRoomId(bandRoomId)
                .dayOfWeek(dayOfWeek)
                .hour(hour)
                .startTime(LocalTime.of(hour, 0))
                .endTime(LocalTime.of(hour + 1 == 24 ? 0 : hour + 1, 0)) // 다음 시간으로 끝나는 슬롯
                .recurrencePattern(RecurrencePattern.valueOf(request.getRecurrencePattern()))
                .isClosed(request.getIsClosed())
                .isOddWeek(request.getIsOddWeek())
                .build();
    }

    public boolean isBandRoomOpen(String bandRoomId, LocalDate date, @Nullable LocalTime time) {
        List<BandRoomTimeSlot> slots = bandRoomTimeSlotRepository.findByBandRoomId(bandRoomId);

        int dayOfWeek = date.getDayOfWeek().getValue(); // 월=1 ~ 일=7

        List<BandRoomTimeSlot> todaySlots = slots.stream()
                .filter(slot -> slot.getDayOfWeek() == dayOfWeek)
                .toList();


        if (todaySlots.isEmpty()) {
            return false; // 오늘 스케줄이 아예 없음
        }

        // 시간 입력이 있으면 시간도 필터링
        if (time != null) {
            int currentHour = time.getHour();
            todaySlots = todaySlots.stream()
                    .filter(slot -> slot.getHour() == currentHour)
                    .toList();
        }

        // 🔥 주차(홀수/짝수) 필터링 추가
        todaySlots = todaySlots.stream()
                .filter(slot -> {
                    if (slot.getRecurrencePattern() == RecurrencePattern.BIWEEKLY) {
                        boolean isCurrentWeekOdd = (date.get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR) % 2) == 1;
                        return slot.getIsOddWeek() == null || slot.getIsOddWeek().equals(isCurrentWeekOdd);
                    }
                    return true; // WEEKLY, MONTHLY는 그냥 통과
                })
                .toList();

        if (todaySlots.isEmpty()) {
            return false; // 주차 필터링 후 슬롯 없음
        }

        // isClosed == true가 하나라도 있으면 무조건 false
        for (BandRoomTimeSlot slot : todaySlots) {
            if (Boolean.TRUE.equals(slot.getIsClosed())) {
                return false; // 닫혀 있는 슬롯 발견
            }
        }

        return true; // 모두 열려 있으면 true
    }




    @Transactional(readOnly = true)
    public List<String> findAvailableRooms(List<String> bandRoomIds, LocalDate date, LocalTime time) {
        return bandRoomIds.stream()
                .filter(id -> isBandRoomOpen(id, date, time))
                .toList();
    }
}

