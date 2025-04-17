package dding.timeManager.controller.BandRoom;

import dding.timeManager.dto.request.BandRoom.BandRoomWeekRequest;
import dding.timeManager.dto.request.BandRoom.UpdateBandRoomWeekRequest;
import dding.timeManager.service.BandRoom.BandRoomWeekService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/time-manager/band-rooms")
@RequiredArgsConstructor
public class BandRoomWeekController {

    private final BandRoomWeekService bandRoomWeekService;

    @PostMapping("/{bandRoomId}/day")
    public String registerBandRoomWeek(
            @PathVariable String bandRoomId,
            @RequestBody BandRoomWeekRequest request
    ) {
        bandRoomWeekService.saveBandRoomWeek(bandRoomId, request);
        return "success";
    }

    @PostMapping("/{bandRoomId}/weeks")
    public String registerBandRoomWeeks(
            @PathVariable String bandRoomId,
            @RequestBody List<BandRoomWeekRequest> requests
    ) {
        bandRoomWeekService.saveBandRoomWeeks(bandRoomId, requests);
        return "success";
    }

    @PutMapping("/{bandRoomId}/weeks")
    public void updateBandRoomWeeks(
            @PathVariable String bandRoomId,
            @RequestParam(name = "forceStudioUpdate", defaultValue = "false") boolean forceStudioUpdate,
            @RequestBody UpdateBandRoomWeekRequest updateRequest
    ) {
        bandRoomWeekService.updateBandRoomWeeks(bandRoomId, updateRequest, forceStudioUpdate);
    }
}
