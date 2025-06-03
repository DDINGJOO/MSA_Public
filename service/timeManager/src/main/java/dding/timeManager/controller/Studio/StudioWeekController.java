package dding.timeManager.controller.Studio;

import dding.timeManager.dto.request.Stodio.StudioWeeksDto;

import dding.timeManager.service.Studio.StudioWeekService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/time-manager/studios")
@RequiredArgsConstructor
public class StudioWeekController {

    private final StudioWeekService studioWeekService;

    @PostMapping("/{bandRoomId}/{studioId}/weeks")
    public void registerStudioWeeks(
            @PathVariable(name = "bandRoomId") String bandRoomId,
            @PathVariable(name = "studioId") String studioId,
            @RequestBody List<StudioWeeksDto> request
    ) {
        studioWeekService.upDateStudioWeeks(studioId,bandRoomId,request);
    }

    @PostMapping("/{bandRoomId}/{studioId}/weeks/upDate")
    public void upDateStudioWeeks(
            @PathVariable(name = "bandRoomId") String bandRoomId,
            @PathVariable(name = "studioId") String studioId,
            @RequestBody List<StudioWeeksDto> request
    ) {
        studioWeekService.upDateStudioWeeks(studioId, bandRoomId, request);
    }

}
