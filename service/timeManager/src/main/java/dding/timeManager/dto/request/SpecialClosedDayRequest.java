package dding.timeManager.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecialClosedDayRequest {

    private String date;   // "yyyy-MM-dd" 포맷
    private String reason; // 예: 정기 점검, 내부 수리
}

