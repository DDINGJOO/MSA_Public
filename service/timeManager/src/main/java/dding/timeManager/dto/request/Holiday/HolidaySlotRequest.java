package dding.timeManager.dto.request.Holiday;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class HolidaySlotRequest {

    private String studioId;
    private String bandRoomId;
    private LocalDate date;

    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isClosed;
    private Integer specialPrice;

}
