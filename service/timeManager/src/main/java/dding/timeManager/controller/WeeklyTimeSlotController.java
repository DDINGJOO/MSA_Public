package dding.timeManager.controller;


import dding.timeManager.config.TimeSlot;
import dding.timeManager.dto.request.WeeklyTimeSlotRequest;
import dding.timeManager.dto.response.WeeklyTimeSlotResponse;
import dding.timeManager.service.WeeklyTimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("/api/weeklyTimeSlot")
@RequiredArgsConstructor
public class WeeklyTimeSlotController {
    private final WeeklyTimeSlotService weeklyTimeSlotService;

    @PostMapping("/timeslots")
    public HashMap<Integer, List<Integer>> upDateTimeSlots(@RequestBody
                                                     List<WeeklyTimeSlotRequest> req)
    {
        for(WeeklyTimeSlotRequest slot : req)
        {
            TimeSlot timeSlot = weeklyTimeSlotService.createTimeSlot(slot.getDayOfWeek(), slot.getHour());
            weeklyTimeSlotService.upDateTimeSlot(slot, timeSlot);
        }

        return weeklyTimeSlotService.readsAllByObjectId(req.getFirst().getObjectId());
    }


    @GetMapping("/timeslots/{ObjectId}")
    public HashMap<Integer, List<Integer>> getSlotsByObjectId(@PathVariable(
            name ="ObjectId") String ObjectId)
    {
        return weeklyTimeSlotService.readsAllByObjectId(ObjectId);
    }

    @DeleteMapping("timeslots/{ObjectId}")
    public void deleteAllByObjectId(@PathVariable(
                                    name = "ObjectId") String ObjectId)
    {
        weeklyTimeSlotService.deleteAllByObjectId(ObjectId);
    }




}
