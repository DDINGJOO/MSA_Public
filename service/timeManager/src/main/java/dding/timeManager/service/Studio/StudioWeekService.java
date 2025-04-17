package dding.timeManager.service.Studio;


import dding.timeManager.dto.request.BandRoom.BandRoomWeekRequest;
import dding.timeManager.dto.request.Stodio.StudioWeekRequest;
import dding.timeManager.entity.Studio.StudioWeek;
import dding.timeManager.repository.StudioWeekRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudioWeekService {

    private final StudioWeekRepository studioWeekRepository;


    @Transactional
    public void saveStudioWeek(String studioId, StudioWeekRequest request) {
//        deleteOverlappingSlots(studioId, request.getDayOfWeek(), request.getStartTime(), request.getEndTime());
            //studioWeekRepository.deleteByStudioIdAndDayOfWeek(studioId, request.getDayOfWeek());

        List<StudioWeek> slots = splitIntoSlots(studioId, request);
        studioWeekRepository.saveAll(slots);
    }

    @Transactional
    public void saveStudioWeeks(String studioId, List<StudioWeekRequest> requests) {
        for (StudioWeekRequest request : requests) {
            studioWeekRepository.deleteByStudioIdAndDayOfWeek(studioId, request.getDayOfWeek());
            saveStudioWeek(studioId, request); //
        }
    }
    private void deleteOverlappingSlots(String studioId, int dayOfWeek, LocalTime startTime, LocalTime endTime) {
        List<StudioWeek> existingSlots = studioWeekRepository.findByStudioIdAndDayOfWeek(studioId, dayOfWeek);

        for (StudioWeek slot : existingSlots) {
            LocalTime slotTime = LocalTime.of(slot.getHour(), 0);

            boolean overlap;
            if (startTime.isBefore(endTime)) {
                overlap = !startTime.isAfter(slotTime) && slotTime.isBefore(endTime);
            } else {
                // Cross-day
                overlap = !startTime.isAfter(slotTime) || slotTime.isBefore(endTime);
            }

            if (overlap) {
                studioWeekRepository.delete(slot);
            }
        }
    }


    private List<StudioWeek> splitIntoSlots(String studioId, StudioWeekRequest request) {
        List<StudioWeek> slots = new ArrayList<>();
        int start = request.getStartTime().getHour();
        int end = request.getEndTime().getHour();
        int dayOfWeek = request.getDayOfWeek();

        if (end <= start) {
            for (int hour = start; hour < 24; hour++) {
                slots.add(createSlot(studioId, dayOfWeek, hour, request));
            }
            int nextDay = (dayOfWeek + 1) % 7;
            for (int hour = 0; hour < end; hour++) {
                slots.add(createSlot(studioId, nextDay, hour, request));
            }
        } else {
            for (int hour = start; hour < end; hour++) {
                slots.add(createSlot(studioId, dayOfWeek, hour, request));
            }
        }
        return slots;
    }



    private StudioWeek createSlot(String studioId, int dayOfWeek, int hour, StudioWeekRequest request) {
        return StudioWeek.builder()
                .studioId(studioId)
                .dayOfWeek(dayOfWeek)
                .hour(hour)
                .recurrencePattern(request.getRecurrencePattern())
                .isOddWeek(request.getIsOddWeek())
                .isClosed(request.isClosed())
                .specialPrice(request.getSpecialPrice())
                .build();
    }



    @Transactional
    public void updateStudioWeeksBasedOnBandRoom(String bandRoomId, String studioId, List<BandRoomWeekRequest> bandRoomRequests) {
        // 1. 기존 스튜디오 주간 슬롯 삭제
        studioWeekRepository.deleteByStudioId(studioId);

        // 2. BandRoom 운영시간 기반으로 새로 생성
        for (BandRoomWeekRequest request : bandRoomRequests) {
            int dayOfWeek = request.getDayOfWeek();
            LocalTime startTime = request.getStartTime();
            LocalTime endTime = request.getEndTime();

            // startTime부터 endTime까지 1시간 단위로 잘라서 저장
            LocalTime current = startTime;
            while (true) {
                // cross-day 처리 (예: 09:00~02:00)
                if (startTime.isAfter(endTime) && (current.equals(LocalTime.MIDNIGHT) || current.isAfter(endTime))) {
                    break;
                }
                if (!startTime.isAfter(endTime) && current.equals(endTime)) {
                    break;
                }

                StudioWeek studioWeek = StudioWeek.builder()
                        .studioId(studioId)
                        .dayOfWeek(dayOfWeek)
                        .hour(current.getHour())
                        .isClosed(request.isClosed())
                        .recurrencePattern(request.getRecurrencePattern())
                        .isOddWeek(request.getIsOddWeek())
                        .build();
                studioWeekRepository.save(studioWeek);

                current = current.plusHours(1);
                if (current.equals(LocalTime.MIDNIGHT)) {
                    // 하루 넘어가면 요일도 1 증가 (0~6, 일~토)
                    dayOfWeek = (dayOfWeek + 1) % 7;
                }
            }
        }
    }
}
