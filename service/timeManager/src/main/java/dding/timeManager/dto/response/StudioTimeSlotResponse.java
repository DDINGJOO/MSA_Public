package dding.timeManager.dto.response;

import dding.timeManager.config.RecurrencePattern;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class StudioTimeSlotResponse {

    private Long id;
    private String studioId;
    private LocalDate date;
    private Integer dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isClosed;
    private Integer price;
    private Boolean isOddWeek;
    private RecurrencePattern recurrencePattern;
}
