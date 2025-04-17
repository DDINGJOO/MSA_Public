package dding.timeManager.controller.BandRoom;



import dding.timeManager.dto.request.BandRoomTimeSlotRequest;
import dding.timeManager.dto.response.BandRoomTimeSlotResponse;
import dding.timeManager.service.BandRoom.BandRoomTimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/time-manager/band-rooms")
@RequiredArgsConstructor
public class BandRoomTimeSlotController {

    private final BandRoomTimeSlotService bandRoomTimeSlotService;

    // 특정 밴드룸에 타임 슬롯 여러 개 등록
    @PostMapping("/{bandRoomId}/schedule-rules")
    public String saveTimeSlots(
            @PathVariable String bandRoomId,
            @RequestBody List<BandRoomTimeSlotRequest> requests
    ) {
        return bandRoomTimeSlotService.saveAll(bandRoomId, requests);
    }

    // 특정 밴드룸에 타임 슬롯 전체 수정 (기존 삭제 후 저장)
    @PutMapping("/{bandRoomId}/schedule-rules")
    public void updateTimeSlots(
            @PathVariable String bandRoomId,
            @RequestBody List<BandRoomTimeSlotRequest> requests
    ) {
        bandRoomTimeSlotService.updateTimeSlots(bandRoomId, requests);
    }

    // 특정 밴드룸의 타임 슬롯 조회
    @GetMapping("/{bandRoomId}/schedule-rules")
    public List<BandRoomTimeSlotResponse> getTimeSlots(
            @PathVariable String bandRoomId
    ) {
        return bandRoomTimeSlotService.getByBandRoom(bandRoomId);
    }

    // 개별 타임 슬롯 삭제
    @DeleteMapping("/schedule-rules/{slotId}")
    public void deleteTimeSlot(
            @PathVariable Long slotId
    ) {
        bandRoomTimeSlotService.delete(slotId);
    }

    // 특정 밴드룸의 모든 타임 슬롯 삭제
    @DeleteMapping("/{bandRoomId}/schedule-rules")
    public void deleteAllTimeSlots(
            @PathVariable String bandRoomId
    ) {
        bandRoomTimeSlotService.deleteAll(bandRoomId);
    }
}

