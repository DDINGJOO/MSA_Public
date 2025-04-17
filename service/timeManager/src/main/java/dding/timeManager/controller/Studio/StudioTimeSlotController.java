package dding.timeManager.controller.Studio;

import dding.timeManager.dto.request.Stodio.StudioTimeSlotRequest;
import dding.timeManager.service.Studio.StudioTimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/time-manager/studios")
@RequiredArgsConstructor
public class StudioTimeSlotController {

    private final StudioTimeSlotService studioTimeSlotService;

    @PostMapping("/{studioId}/special-days")
    public void registerStudioSpecialDay(
            @PathVariable String studioId,
            @RequestBody StudioTimeSlotRequest request
    ) {
        studioTimeSlotService.saveStudioTimeSlot(studioId, request);
    }
}
