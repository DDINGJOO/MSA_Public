package dding.timeManager.controller;


import dding.timeManager.service.CommonTimeSlotsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController("/api/commonTimeSlots")
@RequiredArgsConstructor
public class CommonTimeSlotsController {
    private final CommonTimeSlotsService commonTimeSlotsService;


    @GetMapping("/{ObjectId}")
    public Map<Integer, List<Integer>> getCommonSlotsByObjectId(@PathVariable (name ="ObjectId") String ObjectId)
    {
        return commonTimeSlotsService.readAllByObjectId(ObjectId);
    }


    @PostMapping("/{ObjectId}")
    public Map<Integer, List<Integer>> createCommonSlots(@PathVariable(name = "ObjectId") String ObjectId,@RequestBody List<String> clientIds)
    {
        return commonTimeSlotsService.createCommonSlot(ObjectId, commonTimeSlotsService.getCommonSlotsByClientIds(clientIds));
    }
}
