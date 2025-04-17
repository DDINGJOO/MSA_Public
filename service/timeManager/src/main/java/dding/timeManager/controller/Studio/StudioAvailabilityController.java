package dding.timeManager.controller.Studio;

import dding.timeManager.dto.response.Avaiable.AvailableHourResponse;
import dding.timeManager.service.Studio.StudioAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/time-manager/studios")
@RequiredArgsConstructor
public class StudioAvailabilityController {

    private final StudioAvailabilityService studioAvailabilityService;

    @GetMapping("/{bandRoomId}/{studioId}/open-check")
    public boolean isStudioOpen(
            @PathVariable(name = "bandRoomId") String bandRoomId,
            @PathVariable(name = "studioId") String studioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time
    ) {
        return studioAvailabilityService.isStudioOpen(bandRoomId,studioId, date, time);
    }

    @GetMapping("/{bandRoomId}/{studioId}/available-hours")
    public List<AvailableHourResponse> findAvailableHours(
            @PathVariable(name= "bandRoomId") String bandRoomId,
            @PathVariable(name = "studioId") String studioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return studioAvailabilityService.findAvailableHours(bandRoomId, studioId, date);
    }
}
