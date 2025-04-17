package dding.timeManager.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SpecialClosedDayResponse {

    private String id;
    private String date;   // "yyyy-MM-dd"
    private String reason;
}
