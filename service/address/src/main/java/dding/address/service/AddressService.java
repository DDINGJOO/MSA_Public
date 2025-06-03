package dding.address.service;

import dding.address.config.AddressType;
import dding.address.dto.request.AddressSaveRequest;
import dding.address.dto.response.AddressResponse;
import dding.address.dto.response.KakaoRegionDto;
import dding.address.entity.Address;
import dding.address.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final KakaoService kakaoService;

    public AddressResponse saveAddress(AddressSaveRequest request) {
        KakaoRegionDto dto = kakaoService.getAddressRegions(request.getRoadAddress());
        Address address = Address.builder()
                .addressType(AddressType.BAND_ROOM)
                .referenceId(request.getReferenceId())
                .city(dto.getRegion1())
                .district(dto.getRegion2())
                .neighborhood(dto.getRegion3())
                .roadAddress(request.getRoadAddress())
                .displayAddress(request.getDisplayAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();

        Address saved = addressRepository.save(address);
        return AddressResponse.from(saved);
    }



    public AddressResponse findByReference(AddressType addressType, String referenceId) {
        Address address = addressRepository.findByReferenceIdAndAddressType(referenceId, addressType)
                .orElseThrow(() -> new IllegalArgumentException("해당 주소가 존재하지 않습니다."));
        return AddressResponse.from(address);
    }

    public void deleteByReference(AddressType addressType, String referenceId) {
        Address address = addressRepository.findByReferenceIdAndAddressType(referenceId, addressType)
                .orElseThrow(() -> new IllegalArgumentException("해당 주소가 존재하지 않습니다."));
        addressRepository.delete(address);
    }

    public AddressResponse updateRoadAddress(AddressType addressType, String referenceId, String newRoadAddress) {
        Address address = addressRepository.findByReferenceIdAndAddressType(referenceId, addressType)
                .orElseThrow(() -> new IllegalArgumentException("해당 주소가 존재하지 않습니다."));
        address.updateRoadAddress(newRoadAddress);
        return AddressResponse.from(addressRepository.save(address));
    }


    // 시(도) 목록 조회
    public List<String> getAllCities() {
        return addressRepository.findDistinctCities();
    }

    // 특정 시의 구 목록 조회
    public List<Address> getDistrictsByCity(String city) {
        return addressRepository.findAllByCity(city);
    }

    // 특정 시/구의 동 목록 조회
    public List<String> getNeighborhoods(String city, String district) {
        return addressRepository.findDistinctNeighborhoodsByCityAndDistrict(city, district);
    }


}

