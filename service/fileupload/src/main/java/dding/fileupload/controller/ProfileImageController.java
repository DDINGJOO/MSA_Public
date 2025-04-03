package dding.fileupload.controller;

import dding.fileupload.dto.request.ProfileImageUpdateRequest;
import dding.fileupload.dto.response.ProfileImageUpdateResponse;
import dding.fileupload.entity.ImageFile;
import dding.fileupload.service.ProfileImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile-image")
public class ProfileImageController {

    private final ProfileImageService profileImageService;
    private final FileUploadController fileUploadController;

    @PostMapping("/update")
    public String updateProfileImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") String userId
    ) throws Exception {

        ProfileImageUpdateRequest request = new ProfileImageUpdateRequest();
        request.setFile(file);
        request.setUserId(userId);

        ProfileImageUpdateResponse response = profileImageService.updateProfileImage(request);
        return response.getImageUrl();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Resource> getCntProfileImage(@PathVariable(name = "userId") String userId)
    {
        ImageFile imageFile = profileImageService.getCntProfileImage(userId);
        return fileUploadController.viewImage("profile",imageFile.getId());
    }
}
