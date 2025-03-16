package dding.timeManager.controller;


import dding.timeManager.dto.request.BandRoomScheduleRequest;
import dding.timeManager.dto.response.BandRoomScheduleResponse;
import dding.timeManager.service.BandRoomScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timeslots/bandroom")
@RequiredArgsConstructor
public class BandRoomScheduleController {

    private final BandRoomScheduleService bandRoomScheduleService;

    @PostMapping
    public void save(@RequestBody BandRoomScheduleRequest request) {
        bandRoomScheduleService.save(request);
    }

    @GetMapping("/{bandRoomId}")
    public List<BandRoomScheduleResponse> getByBandRoomAndDateRange(@PathVariable Long bandRoomId,
                                                                    @RequestParam String fromDate,
                                                                    @RequestParam String toDate) {
        return bandRoomScheduleService.findByBandRoomAndDateRange(bandRoomId, fromDate, toDate);
    }

    @DeleteMapping("/{bandRoomId}")
    public void deleteByBandRoomAndDate(@PathVariable Long bandRoomId,
                                        @RequestParam String date) {
        bandRoomScheduleService.deleteByBandRoomAndDate(bandRoomId, date);
    }
}