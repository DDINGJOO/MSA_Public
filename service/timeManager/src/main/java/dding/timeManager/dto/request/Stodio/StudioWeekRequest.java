package dding.timeManager.dto.request.Stodio;

import dding.timeManager.config.RecurrencePattern;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StudioWeekRequest {

    private int dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private RecurrencePattern recurrencePattern;
    private Boolean isOddWeek;
    private boolean isClosed;
    private Integer specialPrice;

}
