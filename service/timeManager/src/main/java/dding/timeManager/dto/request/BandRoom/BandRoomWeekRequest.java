package dding.timeManager.dto.request.BandRoom;

import dding.timeManager.config.RecurrencePattern;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class BandRoomWeekRequest {

    private int dayOfWeek; // 0(일) ~ 6(토)
    private LocalTime startTime; // ex: 09:00
    private LocalTime endTime;   // ex: 02:00 (다음날 새벽 2시)
    private RecurrencePattern recurrencePattern; // WEEKLY, BIWEEKLY
    private Boolean isOddWeek; // 홀수주 여부
    private boolean isClosed; // 휴무 여부
    private Integer specialPrice; // 추가 요금 (nullable)

}
