package dding.timeManager.dto.response.User;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeSlotResponse {
    private Long id;
    private int dayOfWeek;
    private int hour;
}
