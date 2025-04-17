package dding.timeManager.dto.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
public class BandRoomTimeSlotRequest {

    private int dayOfWeek;            // 요일 (1=월 ~ 7=일)
    private String startTime;         // "HH:mm" 포맷
    private String endTime;           // "HH:mm" 포맷
    private String recurrencePattern; // WEEKLY, BIWEEKLY, MONTHLY
    private Boolean isClosed;         // 영업 여부
    private Boolean isOddWeek;        // (BIWEEKLY만 해당)
}
