package dding.fileupload.dto.response;


import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@Setter
@Builder
@AllArgsConstructor
public class PostImagesResponse {
    String postId;
    List<String> uris;
}
