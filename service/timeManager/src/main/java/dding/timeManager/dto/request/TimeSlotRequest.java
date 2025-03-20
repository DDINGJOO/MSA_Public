package dding.timeManager.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotRequest {
    private int dayOfWeek;  // 요일 (0~6, 일~토)
    private int hour;       // 시간 (0~23)
}