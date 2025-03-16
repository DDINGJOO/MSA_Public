package dding.timeManager.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTimeSlotRequest {
    private String userId;
    private String dayOfWeek; // MONDAY ~ SUNDAY
    private int hour;         // 0 ~ 23
    private boolean available;
}
