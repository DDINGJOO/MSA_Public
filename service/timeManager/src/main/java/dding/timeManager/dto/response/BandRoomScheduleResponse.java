package dding.timeManager.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BandRoomScheduleResponse {
    private String date;
    private int hour;
    private boolean available;
    private String memo;
}
