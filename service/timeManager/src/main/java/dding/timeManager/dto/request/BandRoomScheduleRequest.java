package dding.timeManager.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BandRoomScheduleRequest {
    private Long bandRoomId;
    private String date;     // YYYY-MM-DD
    private int hour;        // 0 ~ 23
    private boolean available;
    private String memo;
}