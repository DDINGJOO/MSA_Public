package dding.timeManager.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyTimeSlotRequest {
    private String objectId;
    private String dayOfWeek; // Mon...
    private String hour;         // 0 ~ 23
    private boolean available;
}
