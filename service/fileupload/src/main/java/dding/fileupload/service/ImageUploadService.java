package dding.fileupload.service;

import dding.fileupload.config.ImageStatus;
import dding.fileupload.dto.request.ImageUploadRequest;
import dding.fileupload.entity.ImageFile;
import dding.fileupload.repository.ImageFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageUploadService {

    private final ImageFileRepository imageFileRepository;

    // 실제 저장될 디렉토리 루트 경로
    //TODO : 루트 절대경로 1
    private final String uploadRootPath = "C:/upload/images"; // 예시 (Linux에서는 /var/www/images)

    public String saveImage(ImageUploadRequest request) throws IOException {
        MultipartFile file = request.getFile();
        String category = request.getCategory(); // 예: profile, board, concert

        // 원본 파일명 & 확장자 추출
        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);

        // 저장할 파일명 UUID
        String uuid = UUID.randomUUID().toString();
        String savedName = uuid + "." + extension;

        // 저장 경로 구성: /{root}/{category}/uuid.ext
        String saveDirPath = uploadRootPath + "/" + category;
        File saveDir = new File(saveDirPath);
        if (!saveDir.exists()) saveDir.mkdirs();

        File savedFile = new File(saveDirPath + "/" + savedName);
        file.transferTo(savedFile);

        // DB에 저장
        ImageFile imageFile = ImageFile.builder()
                .id(uuid)
                .originalName(originalFilename)
                .savedName(savedName)
                .path(saveDirPath)
                .category(category)
                .status(ImageStatus.TEMP) // 기본은 TEMP
                .createdAt(LocalDateTime.now())
                .build();

        imageFileRepository.save(imageFile);

        // 리턴: 파일 ID(UUID)
        return uuid;
    }

    public void deleteImage(String imageId) {
        ImageFile imageFile = imageFileRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("이미지를 찾을 수 없습니다: " + imageId));

        // 실제 파일 경로
        String filePath = imageFile.getPath() + "/" + imageFile.getSavedName();

        // 1. 파일 시스템에서 삭제
        try {
            Files.deleteIfExists(Path.of(filePath));
        } catch (IOException e) {
            throw new RuntimeException("파일 삭제 실패: " + e.getMessage());
        }

        // 2. DB에서 삭제 (또는 상태만 DELETED 처리도 가능)
        imageFileRepository.delete(imageFile);
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) return "";
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }


    public String getImageSaveName(String imageId) {


         return  imageFileRepository.findById(imageId).orElseThrow().getSavedName();

    }
}

