package dding.address.service;

import dding.address.config.AddressType;
import dding.address.dto.request.AddressSaveRequest;
import dding.address.entity.Address;
import dding.address.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AddressServiceTest {

    private AddressRepository addressRepository;
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        addressRepository = mock(AddressRepository.class);
        addressService = new AddressService(addressRepository);
    }

    @Test
    void 주소_저장() {
        // given
        AddressSaveRequest request = new AddressSaveRequest();
        request.setAddressType(AddressType.USER);
        request.setReferenceId("user123");
        request.setCity("서울특별시");
        request.setDistrict("강남구");
        request.setNeighborhood("역삼동");
        request.setRoadAddress("서울특별시 강남구 테헤란로 123");
        request.setLegalDongCode("1168010300");
        request.setLatitude(37.12345);
        request.setLongitude(127.12345);

        // when
        addressService.saveAddress(request);

        // then
        ArgumentCaptor<Address> captor = ArgumentCaptor.forClass(Address.class);
        verify(addressRepository).save(captor.capture());

        Address saved = captor.getValue();
        assertThat(saved.getCity()).isEqualTo("서울특별시");
        assertThat(saved.getReferenceId()).isEqualTo("user123");
    }

    @Test
    void 주소_삭제() {
        // given
        Address address = Address.builder()
                .id(1L)
                .referenceId("user123")
                .addressType(AddressType.USER)
                .build();

        when(addressRepository.findByReferenceIdAndAddressType("user123", AddressType.USER))
                .thenReturn(Optional.of(address));

        // when
        addressService.deleteByReference( AddressType.USER,"user123");


        // then
        verify(addressRepository, times(1)).delete(address);
    }
}
