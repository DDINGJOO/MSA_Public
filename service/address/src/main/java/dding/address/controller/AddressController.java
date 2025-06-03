package dding.address.controller;

import dding.address.config.AddressType;
import dding.address.dto.request.AddressSaveRequest;
import dding.address.dto.response.AddressResponse;
import dding.address.entity.Address;
import dding.address.service.AddressService;
import dding.address.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;
    private final KakaoService kakaoService;

    @GetMapping("/{roadUrl}")
    public ResponseEntity<?> getKakaOAddressServiceTest(@PathVariable String roadUrl)
    {
        return ResponseEntity.ok(kakaoService.getKakaoApiFromAddress(roadUrl));
    }

    @GetMapping("/region/{roadUrl}")
    public ResponseEntity<?> getKakaOAddressService(@PathVariable String roadUrl)
    {
        return ResponseEntity.ok(kakaoService.getAddressRegions(roadUrl));
    }

    @PostMapping()
    public ResponseEntity<AddressResponse> save(@RequestBody AddressSaveRequest request) {
        AddressResponse response = addressService.saveAddress(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{type}/{referenceId}")
    public ResponseEntity<AddressResponse> findByReference(@PathVariable("type") AddressType type,
                                                           @PathVariable("referenceId") String referenceId) {
        AddressResponse response = addressService.findByReference(type, referenceId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{type}/{referenceId}")
    public ResponseEntity<Void> delete(@PathVariable("type") AddressType type,
                                       @PathVariable("referenceId") String referenceId) {
        addressService.deleteByReference(type, referenceId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{type}/{referenceId}/road-address")
    public ResponseEntity<AddressResponse> updateRoadAddress(@PathVariable("type") AddressType type,
                                                             @PathVariable("referenceId") String referenceId,
                                                             @RequestBody String newRoadAddress) {
        AddressResponse response = addressService.updateRoadAddress(type, referenceId, newRoadAddress);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cities")
    public ResponseEntity<List<String>> getCities(
    ) {
        return ResponseEntity.ok(addressService.getAllCities());
    }

    @GetMapping("/districts")
    public ResponseEntity<List<Address>> getDistricts(@RequestParam String city) {
        return ResponseEntity.ok(addressService.getDistrictsByCity(city));
    }

    @GetMapping("/neighborhoods")
    public ResponseEntity<List<String>> getNeighborhoods(@RequestParam String city, @RequestParam String district) {
        return ResponseEntity.ok(addressService.getNeighborhoods(city, district));
    }
}
