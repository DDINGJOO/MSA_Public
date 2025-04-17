package dding.timeManager.dto.response.User;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTimeSlotResponse {
    private Long id;
    private String userId;
    private int dayOfWeek;
    private int hour;
}
