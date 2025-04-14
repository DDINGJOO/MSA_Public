package dding.timeManager.controller;

import dding.timeManager.dto.request.StudioTimeSlotBatchRequest;
import dding.timeManager.service.StudioTimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/studios/time-slots")
@RequiredArgsConstructor
public class StudioTimeSlotController {

    private final StudioTimeSlotService timeSlotService;

    @PostMapping
    public ResponseEntity<String> saveTimeSlots(@RequestBody StudioTimeSlotBatchRequest request) {
        timeSlotService.saveTimeSlots(request);
        return ResponseEntity.ok("Time slots saved successfully");
    }

}
