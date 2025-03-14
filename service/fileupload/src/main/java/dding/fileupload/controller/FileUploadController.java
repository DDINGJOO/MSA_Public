package dding.fileupload.controller;


import dding.fileupload.dto.request.ImageUploadRequest;
import dding.fileupload.service.ImageUploadService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class FileUploadController {

    private final ImageUploadService imageUploadService;

    // ✅ 이미지 업로드
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file,
                                         @RequestParam("category") String category) {
        try {
            ImageUploadRequest request = new ImageUploadRequest();
            request.setFile(file);
            request.setCategory(category);

            String imageId = imageUploadService.saveImage(request);
            return ResponseEntity.ok().body("업로드 성공! 이미지 ID : " + imageId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("업로드 실패: " + e.getMessage());
        }
    }

    // ✅ 이미지 삭제
    @DeleteMapping("/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable String imageId) {
        try {
            imageUploadService.deleteImage(imageId);
            return ResponseEntity.ok().body("이미지 삭제 완료!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("삭제 실패: " + e.getMessage());
        }
    }

    @GetMapping("/view/{category}/{filename}")
    public ResponseEntity<Resource> viewImage(@PathVariable String category,
                                              @PathVariable String filename) throws IOException {

        Path path = Path.of("C:/upload/images", category, filename);
        UrlResource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
                .body((Resource) resource);
    }
}
