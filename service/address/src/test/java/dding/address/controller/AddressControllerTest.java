package dding.address.controller;



import com.fasterxml.jackson.databind.ObjectMapper;
import dding.address.config.AddressType;
import dding.address.dto.request.AddressSaveRequest;
import dding.address.service.AddressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AddressController.class)
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("시 목록 조회 API 테스트")
    void getCities() throws Exception {
        when(addressService.getAllCities()).thenReturn(List.of("서울특별시", "부산광역시"));

        mockMvc.perform(get("/api/address/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("서울특별시"))
                .andExpect(jsonPath("$[1]").value("부산광역시"));
    }

    @Test
    @DisplayName("구 목록 조회 API 테스트")
    void getDistricts() throws Exception {
        when(addressService.getDistrictsByCity("서울특별시"))
                .thenReturn(List.of("강남구", "서초구"));

        mockMvc.perform(get("/api/address/districts").param("city", "서울특별시"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("강남구"))
                .andExpect(jsonPath("$[1]").value("서초구"));
    }

    @Test
    @DisplayName("동 목록 조회 API 테스트")
    void getNeighborhoods() throws Exception {
        when(addressService.getNeighborhoods("서울특별시", "강남구"))
                .thenReturn(List.of("역삼동", "논현동"));

        mockMvc.perform(get("/api/address/neighborhoods")
                        .param("city", "서울특별시")
                        .param("district", "강남구"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("역삼동"))
                .andExpect(jsonPath("$[1]").value("논현동"));
    }

    @Test
    void 주소_저장_API() throws Exception {
        AddressSaveRequest request = new AddressSaveRequest();
        request.setAddressType(AddressType.USER);
        request.setReferenceId("user123");
        request.setCity("서울특별시");
        request.setDistrict("강남구");
        request.setNeighborhood("역삼동");
        request.setRoadAddress("서울특별시 강남구 테헤란로 123");
        request.setLegalDongCode("1168010300");
        request.setLatitude(37.1234);
        request.setLongitude(127.1234);

        mockMvc.perform(post("/api/address/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(addressService, times(1)).saveAddress(any(AddressSaveRequest.class));
    }

    @Test
    void 주소_삭제_API() throws Exception {
        mockMvc.perform(delete("/api/address/USER/user123"))
                .andExpect(status().isOk());

        verify(addressService, times(1)).deleteByReference(AddressType.USER,"user123");
    }
}
