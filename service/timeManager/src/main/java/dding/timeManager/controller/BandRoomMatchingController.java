package dding.timeManager.controller;

import dding.timeManager.dto.request.BandCommonTimeRequest;
import dding.timeManager.dto.response.TimeSlotResponse;
import dding.timeManager.service.BandRoomMatchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matching")
@RequiredArgsConstructor
public class BandRoomMatchingController {

    private final BandRoomMatchingService matchingService;

    @PostMapping("/available-times")
    public ResponseEntity<List<TimeSlotResponse>> getMatchingTimes(@RequestBody BandCommonTimeRequest request,
                                                                   @RequestParam String roomId) {
        List<TimeSlotResponse> result = matchingService.getMatchingTimeSlots(
                request.getBandId(),
                request.getUserIds(),
                roomId
        );
        return ResponseEntity.ok(result);
    }
}