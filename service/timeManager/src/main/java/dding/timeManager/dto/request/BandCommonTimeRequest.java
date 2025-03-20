package dding.timeManager.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class BandCommonTimeRequest {
    private String bandId;
    private List<String> userIds;
}