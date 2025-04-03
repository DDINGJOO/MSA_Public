package dding.fileupload.controller;

import dding.fileupload.dto.request.PostImagesRequest;
import dding.fileupload.dto.response.PostImagesResponse;
import dding.fileupload.entity.ImageFile;
import dding.fileupload.service.PostImageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post-images")
public class PostImageController {


    @Value("${server.url}")
    private String serverUrl;
    private final PostImageService postImageService;
    private final FileUploadController fileUploadController;

    @PostMapping("/upload")
    public PostImagesResponse uploadPostImages(
            @RequestParam("file") List<MultipartFile> files,
            @RequestParam("articleId") String articleId
    ) throws Exception {


        PostImagesRequest request = new PostImagesRequest();
        request.setFile(files);
        request.setArticleId(articleId);


        System.out.println(articleId);
        return postImageService.save(request);
    }


    @GetMapping("/ids/{articleId}")
    public List<String> getImageIds(@PathVariable String articleId) {
        return postImageService.getArticleImages(articleId).stream()
                .map(ImageFile::getId)
                .toList();
    }


    @GetMapping("/urls/{articleId}")
    public List<String> getImageUrls(@PathVariable String articleId) {



        return postImageService.getArticleImages(articleId).stream()
                .map(ImageFile::getId)
                .map(id -> serverUrl + "/api/post-images/" + id)
                .toList();
    }


    @GetMapping("/{imageId}")
    public ResponseEntity<Resource> getPostImage(@PathVariable String imageId) {
        return fileUploadController.viewImage("post", imageId);
    }

}
