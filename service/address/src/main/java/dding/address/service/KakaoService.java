package dding.address.service;


import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KakaoService {

    @Value("${KAKAO_KEY}")
    private String key;

    
}
