package dding.fileupload.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ProfileImageUpdateResponse {
    private String imageUrl;
}
