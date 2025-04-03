package dding.fileupload.dto.request;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class PostImagesRequest {


    private List<MultipartFile> file;
    private String articleId;
}
