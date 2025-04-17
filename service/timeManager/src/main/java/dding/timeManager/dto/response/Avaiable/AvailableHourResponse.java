package dding.timeManager.dto.response.Avaiable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AvailableHourResponse {
    private int hour; // 0~23
    private Integer specialPrice; // 특별 가격 (없으면 null)
}
