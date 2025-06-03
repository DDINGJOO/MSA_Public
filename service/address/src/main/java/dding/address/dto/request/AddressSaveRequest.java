package dding.address.dto.request;


import dding.address.config.AddressType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressSaveRequest {
    private AddressType addressType; // USER, BAND_ROOM
    private String referenceId;      // 유저ID or 밴드룸ID


    private String roadAddress;
    private String displayAddress;


    private Double latitude;
    private Double longitude;
}
