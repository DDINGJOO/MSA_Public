package dding.timeManager.service;


import dding.timeManager.config.TimeSlot;
import dding.timeManager.dto.request.WeeklyTimeSlotRequest;
import dding.timeManager.dto.response.WeeklyTimeSlotResponse;
import dding.timeManager.entity.WeeklyTimeSlot;
import dding.timeManager.repository.WeeklyTimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeeklyTimeSlotService {
    WeeklyTimeSlotRepository weeklyTimeSlotRepository;



    public void upDateTimeSlot(WeeklyTimeSlotRequest req, TimeSlot timeSlot)
    {
        String id = generatedKey(req.getObjectId(), timeSlot);
        if(weeklyTimeSlotRepository.existsById(id))
        {
            deleteTimeSlot(id);
        }
        else
        {
            addTimeSlot(id, req.getObjectId(), timeSlot);
        }
    }

    public void deleteAllByObjectId(String ObjectId)
    {
        weeklyTimeSlotRepository.deleteByObjectId(ObjectId);
    }


    public HashMap<Integer, List<Integer>> readsAllByObjectId(String ObjectId)
    {
        List<WeeklyTimeSlot> slots = weeklyTimeSlotRepository.findByObjectId(ObjectId);
        HashMap<Integer,List<Integer>> hm = new HashMap<>();
        for(WeeklyTimeSlot slot : slots)
        {
            if(!hm.containsKey(slot.getDayOfWeek()))
            {
                hm.put(slot.getDayOfWeek(),new ArrayList<Integer>());
            }

            hm.get(slot.getDayOfWeek()).add(slot.getHour());
        }

        return hm;
    }



















    private WeeklyTimeSlotResponse addTimeSlot(String id,String ObjectId, TimeSlot timeSlot)
    {

        WeeklyTimeSlot weeklyTimeSlot = weeklyTimeSlotRepository.save(
                WeeklyTimeSlot.builder()
                        .id(id)
                        .objectId(ObjectId)
                        .dayOfWeek(timeSlot.getDayOfWeek())
                        .hour(timeSlot.getHour())
                        .available(true)
                        .build()
        );

        return new WeeklyTimeSlotResponse(weeklyTimeSlot);
    }

    private void deleteTimeSlot(String id)
    {
        weeklyTimeSlotRepository.deleteById(id);
        return;
    }



    public TimeSlot createTimeSlot(String day ,String hour)
    {
        return new TimeSlot(day, hour);
    }





    private String generatedKey(String ObjectId,TimeSlot timeSlot)
    {
        // ULID 기준 26자 + 5자
        StringBuilder sb = new StringBuilder();
        sb.append(ObjectId);
        sb.append(timeSlot.getDayOfWeek());
        if(timeSlot.getHour()<10)
        {
            sb.append(0);
        }
        sb.append(timeSlot.getHour());

        return sb.toString();

    }

}
