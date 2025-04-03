package dding.fileupload.service;

import dding.fileupload.config.ImageStatus;
import dding.fileupload.dto.request.ProfileImageUpdateRequest;
import dding.fileupload.dto.response.ProfileImageUpdateResponse;
import dding.fileupload.entity.ImageFile;
import dding.fileupload.entity.ProfileImage;
import dding.fileupload.repository.ImageFileRepository;
import dding.fileupload.repository.ProfileImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Value("${server.url}")
    private String serverUrl;
    private final String uploadPath = "C:/upload/images/profile";

    public ImageFile getCntProfileImage(String userId)
    {
        ProfileImage  profileImage= profileImageRepository.findByUserIdAndIsCurrentTrue(userId).orElseThrow();
        return profileImage.getImageFile();
    }

    @Transactional
    public ProfileImageUpdateResponse updateProfileImage(ProfileImageUpdateRequest request) throws IOException {
        MultipartFile file = request.getFile();
        String userId = request.getUserId();

        if (profileImageRepository.existsByUserId(userId)) {
            profileImageRepository.findByUserIdAndIsCurrentTrue(userId)
                    .ifPresent(p -> {
                        p.setCurrent(false);
                        profileImageRepository.save(p);
                    });
        }

        // 파일 저장
        String savedName = fileStorageService.saveFile(file, uploadPath);

        // 이미지 DB 저장
        ImageFile imageFile = ImageFile.builder()
                .id(UUID.randomUUID().toString())
                .originalName(file.getOriginalFilename())
                .savedName(savedName)
                .path(uploadPath)
                .category("profile")
                .status(ImageStatus.CONFIRMED)
                .createdAt(LocalDateTime.now())
                .build();

        imageFileRepository.saveAndFlush(imageFile);
        System.out.println(imageFile.getId());

        // 프로필 이미지 저장
        ProfileImage profileImage = ProfileImage.builder()
                .userId(userId)
                .imageFile(imageFile)
                .isCurrent(true)
                .createdAt(LocalDateTime.now())
                .build();

        profileImageRepository.save(profileImage);

        String imageUrl = serverUrl + "/api/profile-image/" + userId;
        return ProfileImageUpdateResponse.builder()
                .imageUrl(imageUrl)
                .build();

    }
}
