package dding.fileupload.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProfileImageUpdateRequest {
    private MultipartFile file;
    private String userId;   // 인증 모듈에서 받아오는 사용자 ID (loginId or uuid)
}
