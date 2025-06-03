package dding.address.dto.response;

import dding.address.config.AddressType;
import dding.address.entity.Address;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressResponse {
    private Long id;
    private AddressType addressType;
    private String referenceId;

    private String city;
    private String district;
    private String neighborhood;
    private String roadAddress;
    private String legalDongCode;

    private Double latitude;
    private Double longitude;

    public static AddressResponse from(Address address) {
        return AddressResponse.builder()
                .id(address.getId())
                .addressType(address.getAddressType())
                .referenceId(address.getReferenceId())
                .city(address.getCity())

                .district(address.getDistrict())
                .neighborhood(address.getNeighborhood())
                .roadAddress(address.getRoadAddress())
                .legalDongCode(address.getLegalDongCode())
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .build();
    }
}
