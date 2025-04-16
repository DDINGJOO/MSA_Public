package dding.fileupload.controller;


import dding.fileupload.dto.request.ImageUploadRequest;
import dding.fileupload.service.ImageUploadService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images")
public class FileUploadController {

    private final ImageUploadService imageUploadService;
    @Value("${file.upload-root-path}")
    private String uploadRootPath;
    //  이미지 업로드
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

    //  이미지 삭제
    @DeleteMapping("/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable String imageId) {
        try {
            imageUploadService.deleteImage(imageId);
            return ResponseEntity.ok().body("이미지 삭제 완료!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("삭제 실패: " + e.getMessage());
        }
    }


    @GetMapping("/view/{category}/{imageId}")
    public ResponseEntity<Resource> viewImage(@PathVariable String category,
                                              @PathVariable String imageId)  {

        String filename = imageUploadService.getImageSaveName(imageId);
        return printImage(category,filename);
    }


    private ResponseEntity<Resource> printImage(String category, String filename) {

        Path path = Path.of(uploadRootPath, category, filename);
        System.out.println("파일 경로: " + path);

        if (!Files.exists(path)) {
            return ResponseEntity.notFound().build();
        }
        UrlResource resource;
        String contentType;
        try {
            resource = new UrlResource(path.toUri());
             contentType = Files.probeContentType(path);

            if (contentType == null) {
                contentType = "application/octet-stream";
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(resource); //
    }
}
