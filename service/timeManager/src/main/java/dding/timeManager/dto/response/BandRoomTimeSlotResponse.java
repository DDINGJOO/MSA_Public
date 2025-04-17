package dding.timeManager.dto.response;





import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BandRoomTimeSlotResponse {

    private String id;
    private int dayOfWeek;
    private String startTime;
    private String endTime;
    private String recurrencePattern;
    private Boolean isClosed;
    private Boolean isOddWeek;
}
