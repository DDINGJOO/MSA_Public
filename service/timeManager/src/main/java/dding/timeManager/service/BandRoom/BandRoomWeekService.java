package dding.timeManager.service.BandRoom;

import dding.timeManager.dto.request.BandRoom.BandRoomWeekRequest;
import dding.timeManager.dto.request.BandRoom.UpdateBandRoomWeekRequest;
import dding.timeManager.entity.BandRoom.BandRoomWeek;
import dding.timeManager.repository.BandRoomWeekRepository;
import dding.timeManager.service.Studio.StudioWeekService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BandRoomWeekService {

    private final BandRoomWeekRepository bandRoomWeekRepository;
    private final StudioWeekService studioWeekService;

    public void saveBandRoomWeek(String bandRoomId, BandRoomWeekRequest request) {
        // 1. 겹치는 기존 슬롯 삭제
//        deleteOverlappingSlots(bandRoomId, request.getDayOfWeek(), request.getStartTime(), request.getEndTime());

        // 2. 새로운 슬롯 생성
        List<BandRoomWeek> slots = splitIntoSlots(bandRoomId, request);
        bandRoomWeekRepository.saveAll(slots);
    }

    private List<BandRoomWeek> splitIntoSlots(String bandRoomId, BandRoomWeekRequest request) {
        List<BandRoomWeek> slots = new ArrayList<>();
        int start = request.getStartTime().getHour();
        int end = request.getEndTime().getHour();
        int dayOfWeek = request.getDayOfWeek();

        if (end <= start) { // Cross-day (ex. 23:00~02:00)
            for (int hour = start; hour < 24; hour++) {
                slots.add(createSlot(bandRoomId, dayOfWeek, hour, request));
            }
            int nextDay = (dayOfWeek + 1) % 7;
            for (int hour = 0; hour < end; hour++) {
                slots.add(createSlot(bandRoomId, nextDay, hour, request));
            }
        } else {
            for (int hour = start; hour < end; hour++) {
                slots.add(createSlot(bandRoomId, dayOfWeek, hour, request));
            }
        }
        return slots;
    }

    private BandRoomWeek createSlot(String bandRoomId, int dayOfWeek, int hour, BandRoomWeekRequest request) {
        return BandRoomWeek.builder()
                .bandRoomId(bandRoomId)
                .dayOfWeek(dayOfWeek)
                .hour(hour)
                .recurrencePattern(request.getRecurrencePattern())
                .isOddWeek(request.getIsOddWeek())
                .isClosed(request.isClosed())
                .specialPrice(request.getSpecialPrice())
                .build();
    }

    private void deleteOverlappingSlots(String bandRoomId, int dayOfWeek, LocalTime startTime, LocalTime endTime) {
        List<BandRoomWeek> existingSlots = bandRoomWeekRepository.findByBandRoomIdAndDayOfWeek(bandRoomId, dayOfWeek);

        for (BandRoomWeek slot : existingSlots) {
            LocalTime slotTime = LocalTime.of(slot.getHour(), 0);

            boolean overlap;
            if (startTime.isBefore(endTime)) {
                // ex) 09:00 ~ 18:00
                overlap = !startTime.isAfter(slotTime) && slotTime.isBefore(endTime);
            } else {
                // Cross-day ex) 23:00 ~ 02:00
                overlap = !startTime.isAfter(slotTime) || slotTime.isBefore(endTime);
            }

            if (overlap) {
                bandRoomWeekRepository.delete(slot);
            }
        }
    }

    @Transactional
    public void saveBandRoomWeeks(String bandRoomId, List<BandRoomWeekRequest> requests) {
        for (BandRoomWeekRequest request : requests) {
            bandRoomWeekRepository.deleteByBandRoomIdAndDayOfWeek(bandRoomId, request.getDayOfWeek());
            saveBandRoomWeek(bandRoomId, request);
        }
    }

    public void updateBandRoomWeeks(String bandRoomId, UpdateBandRoomWeekRequest updateRequest, boolean forceStudioUpdate) {
        List<BandRoomWeekRequest> weeks = updateRequest.getWeeks();
        List<String> studioIds = updateRequest.getStudioIds();

        // 1. 기존 BandRoom 주간 슬롯 삭제


        // 2. 새 BandRoom 주간 슬롯 저장
        saveBandRoomWeeks(bandRoomId, weeks);

        // 3. forceStudioUpdate 요청이 true면 스튜디오 업데이트
        if (forceStudioUpdate && studioIds != null) {
            for (String studioId : studioIds) {
                studioWeekService.updateStudioWeeksBasedOnBandRoom(bandRoomId, studioId, weeks);
            }
        }
    }
}
