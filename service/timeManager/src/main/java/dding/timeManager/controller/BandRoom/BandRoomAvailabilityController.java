package dding.timeManager.controller.BandRoom;

import dding.timeManager.dto.request.AvailableRoomRequest;
import dding.timeManager.dto.request.AvailableRoomsRequest;
import dding.timeManager.service.BandRoom.BandRoomTimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/time-manager/band-rooms")
@RequiredArgsConstructor
public class BandRoomAvailabilityController {

    private final BandRoomTimeSlotService availabilityService;

    // 특정 밴드룸 - 날짜만 입력해서 열려 있는지 확인
    @GetMapping("/{bandRoomId}/availability")
    public boolean isBandRoomAvailable(
            @PathVariable String bandRoomId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "time", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time
    ) {
        return availabilityService.isBandRoomOpen(bandRoomId, date, time);
    }

    // 여러 밴드룸 중 날짜+시간에 열려 있는 방 리스트
    @GetMapping("/availability/search")
    public List<String> findAvailableBandRooms(
            @RequestBody AvailableRoomsRequest request
    ) {
        return availabilityService.findAvailableRooms(request.getBandRoomIds(), request.getDate(), request.getTime());
    }


    @GetMapping("/{bandRoomId}/open-check")
    public boolean checkBandRoomOpen(
            @PathVariable String bandRoomId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "time", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time
    ) {
        return availabilityService.isBandRoomOpen(bandRoomId, date, time);
    }
}
