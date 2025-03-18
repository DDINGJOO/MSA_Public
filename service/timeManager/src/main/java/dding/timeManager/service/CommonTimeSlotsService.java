package dding.timeManager.service;

import dding.timeManager.entity.CommonTimeSlots;
import dding.timeManager.entity.WeeklyTimeSlot;
import dding.timeManager.repository.CommonTimeSlotsRepository;
import dding.timeManager.repository.WeeklyTimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class CommonTimeSlotsService {

    WeeklyTimeSlotRepository weeklyTimeSlotRepository;
    CommonTimeSlotsRepository commonTimeSlotsRepository;
    public HashMap<Integer, List<Integer>> createCommonSlot(String ObjectId, Map<Integer, List<Integer>> req)
    {
        for(int day : req.keySet())
        {
            for(int hour : req.get(day))
            {
                commonTimeSlotsRepository.save(
                        CommonTimeSlots.builder()
                                .id(generatedKey(ObjectId,day,hour))
                                .ObjectId(ObjectId)
                                .dayOfWeek(day)
                                .hour(hour)
                                .build()
                );
            }

        }
        return readAllByObjectId(ObjectId);
    }

    public HashMap<Integer, List<Integer>> readAllByObjectId(String ObjectId)
    {
        List<CommonTimeSlots> slots =  commonTimeSlotsRepository.findAllByObjectId(ObjectId);
        HashMap<Integer, List<Integer>> hm = new HashMap<>();
        for(CommonTimeSlots slot : slots)
        {
            if(!hm.containsKey(slot.getDayOfWeek()))
            {
                hm.put(slot.getDayOfWeek(), new ArrayList<>());
            }
            hm.get(slot.getDayOfWeek()).add(slot.getHour());
        }

        return hm;
    }

    public Map<Integer, List<Integer>> getCommonSlotsByClientIds(List<String> clientIds)
    {
        List<WeeklyTimeSlot> firstSlots = weeklyTimeSlotRepository.findByObjectId(clientIds.getFirst());
        Map<String, Integer> hm = new HashMap<>();
        List<String> result = new ArrayList<>();


        for(WeeklyTimeSlot slot : firstSlots)
        {
            hm.put(convertString(slot),0);
        }


        for(String ClientId : clientIds)
        {
            for(WeeklyTimeSlot slot : weeklyTimeSlotRepository.findByObjectId(ClientId))
            {
                hm.put(convertString(slot), hm.get(convertString(slot)));
            }
        }


        for(String str : hm.keySet()) {
            if (hm.get(str) == clientIds.size()){
                result.add(str);
            }
        }

        return convertMapIntInts(result);
    }







    private String generatedKey(String id, int yoil, int hour)
    {
            StringBuilder sb = new StringBuilder();
            sb.append(id);
            sb.append(yoil);
            if(hour<10)
            {
                sb.append(0);
            }
            sb.append(hour);
            return sb.toString();
    }


    private Map<Integer, List<Integer>> convertMapIntInts(List<String> strs)
    {
        Map<Integer, List<Integer>> hm = new HashMap<>();
        for(String str : strs)
        {
            int slot = Integer.parseInt(str);
            if(hm.containsKey(slot/100))
            {
                hm.get(slot/100).add(slot%100);
            }

            else
            {
                hm.put(slot/100,new ArrayList<>());
            }

            // 0 요일 = 00시 ,00 시

        }
        return hm;
    }

    private String convertString(WeeklyTimeSlot slot)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(slot.getDayOfWeek());
        if(slot.getHour()<10)
        {
            sb.append(0);
        }
        sb.append(slot.getHour());
        return sb.toString();
    }

}
