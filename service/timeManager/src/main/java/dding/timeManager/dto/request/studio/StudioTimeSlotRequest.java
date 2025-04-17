package dding.timeManager.dto.request.studio;

import dding.timeManager.config.RecurrencePattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class StudioTimeSlotRequest {

    private String studioId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;         // 특정 날짜 (Optional)

    private Integer dayOfWeek;      // 요일 (Optional)

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime startTime;    // 시작 시간 (변경됨)

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime endTime;      // 종료 시간 (변경됨)

    private boolean isClosed;

    private Integer price;

    private Boolean isOddWeek;

    private RecurrencePattern recurrencePattern;
}
