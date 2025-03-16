package dding.timeManager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTimeSlotResponse {
    private String dayOfWeek;
    private int hour;
    private boolean available;
}