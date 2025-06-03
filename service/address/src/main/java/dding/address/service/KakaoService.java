package dding.address.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dding.address.dto.response.KakaoRegionDto;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Service
public class KakaoService {

    @Value("${KAKAO_KEY}")
    private String key;
    private final ObjectMapper objectMapper;

    public String getKakaoApiFromAddress(String roadFullAddr) {
        String apiUrl = "https://dapi.kakao.com/v2/local/search/address.json";
        try {
            // 1) 주소 인코딩
            String encodedAddr = URLEncoder.encode(roadFullAddr, StandardCharsets.UTF_8);
            System.out.println(encodedAddr);
            URL url = new URL(apiUrl + "?query=" + encodedAddr);
            System.out.println(url);

            // 2) HttpURLConnection 캐스팅 후 GET, 헤더 설정
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "KakaoAK " + key);

            // 3) 응답 코드 확인 & 스트림 선택
            int responseCode = conn.getResponseCode();
            InputStream is = (responseCode == HttpURLConnection.HTTP_OK)
                    ? conn.getInputStream()
                    : conn.getErrorStream();

            // 4) 결과 읽기
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();

            // 5) 에러면 예외 던지기
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException(
                        "Kakao API Error. HTTP " + responseCode + ": " + sb.toString()
                );
            }

            return sb.toString();

        } catch (IOException e) {
            throw new RuntimeException("Kakao API 호출 중 예외 발생", e);
        }
    }

    public KakaoRegionDto getAddressRegions(String roadFullAddr) {
        String json = getKakaoApiFromAddress(roadFullAddr);
        try {
            JsonNode root = objectMapper.readTree(json);
            JsonNode addr = root.path("documents").get(0).path("address");

            String r1 = addr.path("region_1depth_name").asText("");
            String r2 = addr.path("region_2depth_name").asText("");
            String r3 = addr.path("region_3depth_name").asText("");

            return new KakaoRegionDto(r1, r2, r3);
        } catch (IOException e) {
            throw new RuntimeException("Kakao JSON 파싱 오류", e);
        }
    }
}
