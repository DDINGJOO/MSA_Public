package dding.timeManager.service;


import dding.timeManager.dto.response.TimeSlotResponse;
import dding.timeManager.entity.BandRoomSchedule;
import dding.timeManager.repository.BandRoomScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BandRoomMatchingService {

    private final CommonAvailableTimeService commonAvailableTimeService;
    private final BandRoomScheduleRepository bandRoomScheduleRepository;

    public List<TimeSlotResponse> getMatchingTimeSlots(String bandId, List<String> userIds, String roomId) {

        // 1 밴드 구성원 공통 가능 시간 조회
        List<TimeSlotResponse> commonTimes = commonAvailableTimeService.getCommonAvailableTimes(bandId, userIds);

        //  해당 합주실의 예약 가능 시간 조회 (available = true만)
        List<BandRoomSchedule> availableSchedules = bandRoomScheduleRepository
                .findByRoomIdAndAvailableTrue(roomId);

        Set<String> roomSlots = availableSchedules.stream()
                .map(slot -> slot.getDayOfWeek() + "_" + slot.getHour())
                .collect(Collectors.toSet());

        //  교집합 수행
        return commonTimes.stream()
                .filter(slot -> roomSlots.contains(slot.getDayOfWeek() + "_" + slot.getHour()))
                .collect(Collectors.toList());
    }
}