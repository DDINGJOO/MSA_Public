package dding.timeManager.dto.request.BandRoom;

import lombok.Data;

import java.util.List;

@Data
public class UpdateBandRoomWeekRequest {
    private List<BandRoomWeekRequest> weeks;
    private List<String> studioIds; // <- 이걸 추가
}
