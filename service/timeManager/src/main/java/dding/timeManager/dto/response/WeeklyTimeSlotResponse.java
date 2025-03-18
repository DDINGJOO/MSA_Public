package dding.timeManager.dto.response;

import dding.timeManager.entity.WeeklyTimeSlot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyTimeSlotResponse {
    private int dayOfWeek;
    private int hour;
    private boolean available;


    public WeeklyTimeSlotResponse(WeeklyTimeSlot weeklyTimeSlot)
    {
        WeeklyTimeSlotResponse response = new WeeklyTimeSlotResponse();
        response.dayOfWeek = weeklyTimeSlot.getDayOfWeek();
        response.hour = weeklyTimeSlot.getHour();
        response.available = weeklyTimeSlot.isAvailable();


    }
}