package dding.timeManager.controller;


import dding.timeManager.dto.request.TimeSlotRequest;
import dding.timeManager.dto.response.TimeSlotResponse;
import dding.timeManager.service.UserTimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/time-slots/user")
@RequiredArgsConstructor
public class UserTimeSlotController {

    private final UserTimeSlotService userTimeSlotService;

    // 유저 시간 슬롯 추가
    @PostMapping("/{userId}")
    public ResponseEntity<String> addUserTimeSlot(@PathVariable String userId,
                                                  @RequestBody TimeSlotRequest request) {
        userTimeSlotService.addTimeSlot(userId, request);
        return ResponseEntity.ok("시간 슬롯이 추가되었습니다.");
    }

    // 특정 유저의 이용 가능 시간 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<TimeSlotResponse>> getUserTimeSlots(@PathVariable String userId) {
        return ResponseEntity.ok(userTimeSlotService.getUserTimeSlots(userId));
    }

    //  특정 시간 슬롯 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserTimeSlot(@PathVariable Long id) {
        userTimeSlotService.deleteUserTimeSlot(id);
        return ResponseEntity.ok("시간 슬롯이 삭제되었습니다.");
    }

    @PostMapping("/{userId}/bulk")
    public ResponseEntity<String> addBulkUserTimeSlots(@PathVariable String userId,
                                                       @RequestBody List<TimeSlotRequest> requests) {
        userTimeSlotService.addTimeSlots(userId, requests);
        return ResponseEntity.ok("여러 시간 슬롯이 추가되었습니다.");
    }


}
