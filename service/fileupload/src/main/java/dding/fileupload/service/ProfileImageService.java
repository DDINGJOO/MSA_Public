package dding.fileupload.service;

import dding.fileupload.config.ImageStatus;
import dding.fileupload.dto.request.ProfileImageUpdateRequest;
import dding.fileupload.dto.response.ProfileImageUpdateResponse;
import dding.fileupload.entity.ImageFile;
import dding.fileupload.entity.ProfileImage;
import dding.fileupload.repository.ImageFileRepository;
import dding.fileupload.repository.ProfileImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileImageService {

    private final FileStorageService fileStorageService;
    private final ImageFileRepository imageFileRepository;
    private final ProfileImageRepository profileImageRepository;

    private final String uploadPath = "C:/upload/images/profile";

    public ProfileImageUpdateResponse updateProfileImage(ProfileImageUpdateRequest request) throws IOException {
        MultipartFile file = request.getFile();
        String userId = request.getUserId();

        // 이전 프로필 비활성화
        profileImageRepository.findByUserIdAndIsCurrentTrue(userId)
                .ifPresent(p -> {
                    p.setCurrent(false);
                    profileImageRepository.save(p);
                });

        // 저장
        String savedName = fileStorageService.saveFile(file, uploadPath);

        ImageFile imageFile = ImageFile.builder()
                .id(UUID.randomUUID().toString())
                .originalName(file.getOriginalFilename())
                .savedName(savedName)
                .path(uploadPath)
                .category("profile")
                .status(ImageStatus.CONFIRMED)
                .createdAt(LocalDateTime.now())
                .build();
        imageFileRepository.save(imageFile);

        ProfileImage profileImage = ProfileImage.builder()
                .userId(userId)
                .imageFile(imageFile)
                .isCurrent(true)
                .createdAt(LocalDateTime.now())
                .build();
        profileImageRepository.save(profileImage);

        return new ProfileImageUpdateResponse("/uploads/profile/" + savedName);
    }
}
