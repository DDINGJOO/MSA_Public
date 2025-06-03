package dding.timeManager.dto.request.Stodio;

import dding.timeManager.config.RecurrencePattern;
import dding.timeManager.entity.Studio.StudioWeek;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudioWeeksDto {

    private int dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private RecurrencePattern recurrencePattern;
    private Boolean isOddWeek;
    private boolean isClosed;
    private Integer specialPrice;



}
