package dding.timeManager.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTimeSlotRequest {
    private String userId;
    private int dayOfWeek; // 0(일) ~ 6(토)
    private int hour;      // 0 ~ 23
}