package dding.timeManager.controller;

import dding.timeManager.dto.request.BandRoomScheduleRequest;
import dding.timeManager.dto.response.BandRoomScheduleResponse;
import dding.timeManager.service.BandRoomScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/time-slots/room")
@RequiredArgsConstructor
public class BandRoomScheduleController {

    private final BandRoomScheduleService scheduleService;

    // 시간 슬롯 추가
    @PostMapping
    public ResponseEntity<String> addSchedule(@RequestBody BandRoomScheduleRequest request) {
        scheduleService.addSchedule(request);
        return ResponseEntity.ok("합주실 스케줄이 등록되었습니다.");
    }

    // 특정 합주실의 특정 날짜 스케줄 조회
    @GetMapping
    public ResponseEntity<List<BandRoomScheduleResponse>> getSchedules(
            @RequestParam String roomId,
            @RequestParam LocalDate date
    ) {
        return ResponseEntity.ok(scheduleService.getSchedulesByRoomAndDate(roomId, date));
    }

    // 스케줄 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok("합주실 스케줄이 삭제되었습니다.");
    }
}