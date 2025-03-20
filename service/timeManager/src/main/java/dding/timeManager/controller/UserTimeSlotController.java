package dding.timeManager.controller;


import dding.timeManager.dto.request.BandCommonTimeRequest;
import dding.timeManager.dto.request.TimeSlotRequest;
import dding.timeManager.dto.response.TimeSlotResponse;
import dding.timeManager.repository.BandRoomScheduleRepository;
import dding.timeManager.repository.BandTimeSlotRepository;
import dding.timeManager.service.CommonAvailableTimeService;
import dding.timeManager.service.UserTimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/time-slots/user")
@RequiredArgsConstructor
public class UserTimeSlotController {

    private final UserTimeSlotService userTimeSlotService;
    private final CommonAvailableTimeService commonAvailableTimeService;
    private final BandRoomScheduleRepository bandRoomScheduleRepository;
    private final BandTimeSlotRepository bandTimeSlotRepository;

    // 유저 시간 슬롯 추가
    @PostMapping("/{userId}")
    public ResponseEntity<String> addUserTimeSlot(@PathVariable String userId,
                                                  @RequestBody TimeSlotRequest request) {
        userTimeSlotService.addTimeSlot(userId, request);
        return ResponseEntity.ok("시간 슬롯이 추가되었습니다.");
    }

    // 특정 유저의 이용 가능 시간 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<TimeSlotResponse>> getUserTimeSlots(@PathVariable String userId) {
        return ResponseEntity.ok(userTimeSlotService.getUserTimeSlots(userId));
    }

    //  특정 시간 슬롯 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserTimeSlot(@PathVariable Long id) {
        userTimeSlotService.deleteUserTimeSlot(id);
        return ResponseEntity.ok("시간 슬롯이 삭제되었습니다.");
    }



    //조회 마가 계산 하긴 하는데... 흠... 먼가 좀 방법이 있어야 할지도?
    @PostMapping("/common")
    public ResponseEntity<List<TimeSlotResponse>> getCommonAvailableTimes(@RequestBody BandCommonTimeRequest request) {
        if(!bandTimeSlotRepository.exisistByBandId(request.getBandId()))
        {
            commonAvailableTimeService.saveAvailableTimes(commonAvailableTimeService.getCommonAvailableTimes(request.getBandId(), request.getUserIds()),request.getBandId());
        }

        return ResponseEntity.ok(
                commonAvailableTimeService.getCommonAvailableTimes(request.getBandId(), request.getUserIds())
        );
    }

    @GetMapping("/common/{bandId}")
    public ResponseEntity<List<TimeSlotResponse>> readCommonAvailableTimes(@PathVariable(name= "bandId") String bandId)
    {
        return ResponseEntity.ok(commonAvailableTimeService.readCommonAvailableTimes(bandId));
    }
}