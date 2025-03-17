package dding.timeManager.service;


import dding.timeManager.dto.response.TimeSlotResponse;
import dding.timeManager.entity.UserTimeSlot;
import dding.timeManager.repository.UserTimeSlotRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor



//TODO : REPACK Time, hour  -> Table
public class BandTimeSlotService {
    UserTimeSlotRepository userTimeSlotRepository;


    public List<TimeSlotResponse> calCommonTimeSlot(List<String> UserIds)
    {
        List<TimeSlotResponse> result = new ArrayList<>();
        var table = createTimeTable();
        for(String userId : UserIds)
        {
            List<UserTimeSlot> slotResponses = userTimeSlotRepository.findByUserId(userId);
            for(UserTimeSlot slot : slotResponses)
            {
                addTime(table, slot.getDayOfWeek(),slot.getHour());
            }
        }


        for(int i =1 ; i < table.length ; i++)
        {
            for( int j = 0 ; j <table[0].length; j++)
            {
                if(table[i][j] == UserIds.size())
                {
                    TimeSlot slot = new TimeSlot(i,j);
                    TimeSlotResponse timeSlotResponse = new TimeSlotResponse(slot.day,slot.hour);
                    result.add(timeSlotResponse);
                }
            }
        }

        return result;
    }

    private int[][] createTimeTable()
    {
        return  new int[8][25];// [Day][Hour]

    }
    private void addTime(int[][] table, DayOfWeek dayOfWeek, int hour)
    {
        table[dayOfWeek.getValue()][hour] +=1;
    }

    class TimeSlot
    {
        String day;
        int hour;
        TimeSlot(int day , int hour)
        {
            if(day == 1 )
            {
                this.day = "MONDAY";
            }
            else if(day == 3 )
            {
                this.day = "WEDNESDAY";
            }
            else if(day == 2 )
            {
                this.day = "TUESDAY";
            }
            else if(day == 4 )
            {
                this.day = "THURSDAY";
            }
            else if(day == 5 )
            {
                this.day = "FRIDAY";
            }
            else if(day == 6 )
            {
                this.day = "SATURDAY";
            }
            else if(day == 7 )
            {
                this.day = "SUNDAY";
            }

            this.hour = hour;
        }

    }

}
