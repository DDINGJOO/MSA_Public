package dding.timeManager.service;

import dding.timeManager.config.TimeSlot;
import dding.timeManager.dto.response.TimeSlotResponse;
import dding.timeManager.entity.BandTimeSLot;
import dding.timeManager.repository.BandTimeSlotRepository;
import dding.timeManager.repository.UserTimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonAvailableTimeService {
    private final BandTimeSlotRepository bandTimeSlotRepository;
    private final UserTimeSlotRepository userTimeSlotRepository;


    public List<TimeSlotResponse> getCommonAvailableTimes(String bandId, List<String> userIds) {
        if (userIds == null || userIds.isEmpty()) return Collections.emptyList();

        Set<String> commonSlots = userTimeSlotRepository.findByUserId(userIds.get(0)).stream()
                .map(slot -> slot.getDayOfWeek() + "_" + slot.getHour())
                .collect(Collectors.toSet());

        for (int i = 1; i < userIds.size(); i++) {
            Set<String> currentUserSlots = userTimeSlotRepository.findByUserId(userIds.get(i)).stream()
                    .map(slot -> slot.getDayOfWeek() + "_" + slot.getHour())
                    .collect(Collectors.toSet());

            commonSlots.retainAll(currentUserSlots);
        }




        return commonSlots.stream()
                .map(slotKey -> {
                    int dayOfWeek = Integer.parseInt(slotKey.split("_")[0]);
                    int hour = Integer.parseInt(slotKey.split("_")[1]);
                    return new TimeSlotResponse(null, dayOfWeek, hour);
                }).toList();
    }




    public  List<TimeSlotResponse>  readCommonAvailableTimes(String bandId)
    {
        return bandTimeSlotRepository.findByBandId(bandId).stream()
                .map(slot -> TimeSlotResponse.builder()
                        .id(slot.getId())
                        .dayOfWeek(slot.getDayOfWeek())
                        .hour(slot.getHour())
                        .build())
                .collect(Collectors.toList());



    }



    public void saveAvailableTimes(List<TimeSlotResponse> slots, String bandId)
    {
        for(TimeSlotResponse slot : slots)
        {
            bandTimeSlotRepository.save(
                    BandTimeSLot.builder()
                            .bandId(bandId)
                            .dayOfWeek(slot.getDayOfWeek())
                            .hour(slot.getHour())
                            .build()
            );
        }
    }
}
