package dding.timeManager.dto.request.Stodio;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class StudioTimeSlotRequest {

    private LocalDate date; // yyyy-MM-dd
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isClosed;
    private Integer specialPrice;

}
