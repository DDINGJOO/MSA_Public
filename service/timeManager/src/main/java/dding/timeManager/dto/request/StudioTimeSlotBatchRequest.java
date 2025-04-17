package dding.timeManager.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudioTimeSlotBatchRequest {
    private String studioId;
    private int dayOfWeek;         // 1=월, ..., 7=일
    private String startTime;      // ex. "09:00"
    private String endTime;        // ex. "21:00"
    private boolean available;
    private boolean isHoliday;

}
