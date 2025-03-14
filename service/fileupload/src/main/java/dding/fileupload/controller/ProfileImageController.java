package dding.fileupload.controller;

import dding.fileupload.dto.request.ProfileImageUpdateRequest;
import dding.fileupload.dto.response.ProfileImageUpdateResponse;
import dding.fileupload.service.ProfileImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile-image")
public class ProfileImageController {

    private final ProfileImageService profileImageService;

    @PostMapping("/update")
    public ResponseEntity<ProfileImageUpdateResponse> updateProfileImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") String userId
    ) throws Exception {

        ProfileImageUpdateRequest request = new ProfileImageUpdateRequest();
        request.setFile(file);
        request.setUserId(userId);

        ProfileImageUpdateResponse response = profileImageService.updateProfileImage(request);
        return ResponseEntity.ok(response);
    }
}