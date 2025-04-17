package dding.timeManager.dto.response.studio;

import dding.timeManager.config.RecurrencePattern;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class StudioTimeSlotResponse {

    private Long id;

    private String studioId;

    private LocalDate date;

    private Integer dayOfWeek;

    private Integer hour;

    private boolean isClosed;

    private Integer price;

    private Boolean isOddWeek;

    private RecurrencePattern recurrencePattern;
}
