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


        if(Objects.equals(dayOfWeek,"FRI"))
        {
            this.dayOfWeek  = 5;
            return;

        }
        if(Objects.equals(dayOfWeek,"SAT"))
        {
            this.dayOfWeek  = 6;
            return;
        }
        if(Objects.equals(dayOfWeek,"SUN"))
        {
            this.dayOfWeek  = 7;
            return;
        }

        if(Objects.equals(dayOfWeek,"MON"))
        {
            this.dayOfWeek  = 1;
            return;
        }
        if(Objects.equals(dayOfWeek,"TUE"))
        {
            this.dayOfWeek  = 2;
            return;
        }
        if(Objects.equals(dayOfWeek,"WED"))
        {
            this.dayOfWeek  = 3;
            return;
        }
        if(Objects.equals(dayOfWeek,"THU"))
        {
            this.dayOfWeek  = 4;
            return;
        }


    }
}
