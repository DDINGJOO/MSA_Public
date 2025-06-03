package dding.timeManager.dto.request.BandRoom;

import lombok.Data;

import java.util.List;

@Data
public class UpdateBandRoomWeekRequest {
    private List<BandRoomWeekRequest> weeks;
}
