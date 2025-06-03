package dding.address.entity;


import dding.address.config.AddressType;
import jakarta.persistence.*;
import lombok.*;

@Entity(name ="address")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // USER / BAND_ROOM 구분
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    // 외래키 역할: 유저 ID 또는 합주실 ID
    private String referenceId;




    private String city;         // 시
    private String district;     // 구
    private String neighborhood; // 동
    private String roadAddress;  // 전체 도로명 주소
    private String displayAddress;

    private String legalDongCode; // 법정동 코드

    private Double latitude;     // 위도
    private Double longitude;    // 경도

    public void updateRoadAddress(String newRoadAddress) {
        this.roadAddress = newRoadAddress;
    }
}

