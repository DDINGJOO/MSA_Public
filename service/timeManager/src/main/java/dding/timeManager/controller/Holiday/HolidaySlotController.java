package dding.timeManager.controller.Holiday;

import dding.timeManager.dto.request.Holiday.HolidaySlotRequest;
import dding.timeManager.service.Holiday.HolidaySlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/time-manager/studios")
@RequiredArgsConstructor
public class HolidaySlotController {

    private final HolidaySlotService holidaySlotService;

    @PostMapping("//holidays")
    public void registerHolidaySlot(
            @RequestBody List<HolidaySlotRequest> requests
    ) {
        holidaySlotService.saveHolidaySlots(requests);
    }
}
