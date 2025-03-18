package dding.timeManager.config;


import lombok.*;

import java.util.Objects;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TimeSlot {

    int dayOfWeek;
    int hour;

    public TimeSlot(String dayOfWeek, String hour) {
        this. hour = Integer.getInteger(hour);


        if(Objects.equals(dayOfWeek,"Friday"))
        {
            this.dayOfWeek  = 5;
            return;

        }
        if(Objects.equals(dayOfWeek,"Saturday"))
        {
            this.dayOfWeek  = 6;
            return;
        }
        if(Objects.equals(dayOfWeek,"Sunday"))
        {
            this.dayOfWeek  = 7;
            return;
        }

        if(Objects.equals(dayOfWeek,"Monday"))
        {
            this.dayOfWeek  = 1;
            return;
        }
        if(Objects.equals(dayOfWeek,"TuesDay"))
        {
            this.dayOfWeek  = 2;
            return;
        }
        if(Objects.equals(dayOfWeek,"Wednesday"))
        {
            this.dayOfWeek  = 3;
            return;
        }
        if(Objects.equals(dayOfWeek,"Thursday"))
        {
            this.dayOfWeek  = 4;
            return;
        }


    }
}
