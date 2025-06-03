package dding.timeManager.controller.BandRoom;

import dding.timeManager.dto.request.BandRoom.BandRoomWeekRequest;
import dding.timeManager.dto.request.BandRoom.UpdateBandRoomWeekRequest;
import dding.timeManager.dto.response.Avaiable.AvailableHourResponse;
import dding.timeManager.service.BandRoom.BandRoomWeekService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
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
    public String updateBandRoomWeeks(
            @PathVariable String bandRoomId,
            @RequestBody List<BandRoomWeekRequest> requests
    ) {
        bandRoomWeekService.updateBandRoomWeeks(bandRoomId, requests);
        return "success";
    }

    @GetMapping("/{bandRoomId}/open-check")
    public ResponseEntity<?> isStudioOpen(
            @PathVariable(name = "bandRoomId") String bandRoomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time
    ) {
        return ResponseEntity.ok(bandRoomWeekService.isOpenCheck(bandRoomId,date, time));
    }
}
