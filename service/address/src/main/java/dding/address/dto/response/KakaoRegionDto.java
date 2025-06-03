package dding.address.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoRegionDto {
    private String region1;   // 예: 인천
    private String region2;   // 예: 부평구
    private String region3;   // 예: 삼산동
}
