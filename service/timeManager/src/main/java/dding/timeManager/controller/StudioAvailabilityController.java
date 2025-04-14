package dding.timeManager.controller;

import dding.timeManager.service.StudioAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/studios")
public class StudioAvailabilityController {

    private final StudioAvailabilityService studioAvailabilityService;

    /**
     * 특정 날짜에 예약 가능한 시간대 리스트 반환
     * 예: GET /api/studios/available-time?studioId=ST123&date=2025-04-20
     */
    @GetMapping("/available-time")
    public ResponseEntity<List<Integer>> getAvailableHours(
            @RequestParam("studioId") String studioId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<Integer> availableHours = studioAvailabilityService.getAvailableReservationHours(studioId, date);
        return ResponseEntity.ok(availableHours);
    }
}
