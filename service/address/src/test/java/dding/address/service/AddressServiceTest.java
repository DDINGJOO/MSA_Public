package dding.address.service;

import dding.address.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AddressReadServiceTest {

    private AddressRepository addressRepository;
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        addressRepository = Mockito.mock(AddressRepository.class);
        addressService = new AddressService(addressRepository);
    }

    @Test
    void 시_목록_조회() {
        // given
        when(addressRepository.findDistinctCities()).thenReturn(List.of("서울특별시", "부산광역시"));

        // when
        List<String> cities = addressService.getAllCities();

        // then
        assertThat(cities).containsExactly("서울특별시", "부산광역시");
    }

    @Test
    void 구_목록_조회() {
        // given
        String city = "서울특별시";
        when(addressRepository.findDistrictsByCity(city)).thenReturn(List.of("강남구", "서초구"));

        // when
        List<String> districts = addressService.getDistrictsByCity(city);

        // then
        assertThat(districts).containsExactly("강남구", "서초구");
    }

    @Test
    void 동_목록_조회() {
        // given
        String city = "서울특별시";
        String district = "강남구";
        when(addressRepository.findNeighborhoodsByCityAndDistrict(city, district))
                .thenReturn(List.of("역삼동", "논현동"));

        // when
        List<String> neighborhoods = addressService.getNeighborhoods(city, district);

        // then
        assertThat(neighborhoods).containsExactly("역삼동", "논현동");
    }
}