package dding.fileupload.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
public class ImageUploadRequest {
    private MultipartFile file;
    private String category;
}