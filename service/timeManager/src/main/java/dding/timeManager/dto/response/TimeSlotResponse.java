package dding.timeManager.dto.response;


import lombok.*;

import java.time.DayOfWeek;


@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TimeSlotResponse {
    private String dayOfWeek;
    private int TimeSlot;
}
