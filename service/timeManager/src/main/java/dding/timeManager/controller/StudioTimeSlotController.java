package dding.timeManager.controller;

import dding.timeManager.dto.request.studio.StudioTimeSlotRequest;
import dding.timeManager.dto.response.studio.StudioTimeSlotResponse;
import dding.timeManager.service.studio.StudioTimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/time-manager/studios")
@RequiredArgsConstructor
public class StudioTimeSlotController {

    private final StudioTimeSlotService studioTimeSlotService;

    // 1. 스튜디오 시간 슬롯 등록
    @PostMapping("/{studioId}/time-slots")
    public void createTimeSlots(
            @PathVariable String studioId,
            @RequestBody List<StudioTimeSlotRequest> requests
    ) {
        studioTimeSlotService.saveAll(studioId, requests);
    }

    // 2. 스튜디오 시간 슬롯 전체 조회
    @GetMapping("/{studioId}/time-slots")
    public List<StudioTimeSlotResponse> getTimeSlots(
            @PathVariable String studioId
    ) {
        return studioTimeSlotService.getAllByStudio(studioId);
    }

    // 3. 스튜디오 시간 슬롯 수정 (전체 삭제 후 새로 저장)
    @PutMapping("/{studioId}/time-slots")
    public void updateTimeSlots(
            @PathVariable String studioId,
            @RequestBody List<StudioTimeSlotRequest> requests
    ) {
        studioTimeSlotService.updateTimeSlots(studioId, requests);
    }

    // 4. 개별 시간 슬롯 삭제
    @DeleteMapping("/time-slots/{timeSlotId}")
    public void deleteTimeSlot(
            @PathVariable Long timeSlotId
    ) {
        studioTimeSlotService.delete(timeSlotId);
    }

    // 5. 스튜디오의 모든 시간 슬롯 삭제
    @DeleteMapping("/{studioId}/time-slots")
    public void deleteAllTimeSlots(
            @PathVariable String studioId
    ) {
        studioTimeSlotService.deleteAllByStudio(studioId);
    }
}
