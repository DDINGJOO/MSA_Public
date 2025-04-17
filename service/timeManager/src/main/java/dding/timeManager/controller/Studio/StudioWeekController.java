package dding.timeManager.controller.Studio;

import dding.timeManager.dto.request.Stodio.StudioWeekRequest;

import dding.timeManager.service.Studio.StudioWeekService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/time-manager/studios")
@RequiredArgsConstructor
public class StudioWeekController {

    private final StudioWeekService studioWeekService;

    @PostMapping("/{studioId}/weeks")
    public void registerStudioWeeks(
            @PathVariable String studioId,
            @RequestBody List<StudioWeekRequest> request
    ) {
        studioWeekService.saveStudioWeeks(studioId,request);
    }

    @PostMapping("/{studioId}/weeks/special-prices")
    public void registerStudioWeek(
            @PathVariable String studioId,
            @RequestBody List<StudioWeekRequest> request
    ) {
        studioWeekService.saveStudioWeeks(studioId,request);
    }
}
