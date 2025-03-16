package dding.timeManager.controller;


import dding.timeManager.dto.request.UserTimeSlotRequest;
import dding.timeManager.dto.response.UserTimeSlotResponse;
import dding.timeManager.service.UserTimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timeslots/user")
@RequiredArgsConstructor
public class UserTimeSlotController {

    private final UserTimeSlotService userTimeSlotService;

    @PostMapping
    public void save(@RequestBody UserTimeSlotRequest request) {
        userTimeSlotService.save(request);
    }

    @GetMapping("/{userId}")
    public List<UserTimeSlotResponse> getByUser(@PathVariable String userId) {
        return userTimeSlotService.findByUserId(userId);
    }

    @DeleteMapping("/{userId}")
    public void deleteByUser(@PathVariable String userId) {
        userTimeSlotService.deleteAllByUserId(userId);
    }
}