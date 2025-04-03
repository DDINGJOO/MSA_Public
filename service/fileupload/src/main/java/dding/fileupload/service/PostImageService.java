package dding.fileupload.service;


import dding.fileupload.config.ImageStatus;
import dding.fileupload.dto.request.PostImagesRequest;
import dding.fileupload.dto.response.PostImagesResponse;
import dding.fileupload.entity.ImageFile;
import dding.fileupload.entity.PostImage;
import dding.fileupload.entity.ProfileImage;
import dding.fileupload.repository.ImageFileRepository;
import dding.fileupload.repository.PostImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostImageService {

    private final PostImageRepository postImageRepository;
    private final ImageFileRepository imageFileRepository;
    private final FileStorageService fileStorageService;
    private final String uploadPath = "C:/upload/images/post";
    @Value("${server.url}")
    private String serverUrl;



    @Transactional
    public PostImagesResponse save(PostImagesRequest req) throws IOException {
        List<MultipartFile> files = req.getFile();
        List<String> uris = new ArrayList<>();


        int index = 0;
        for (MultipartFile file : files) {

            String savedName = fileStorageService.saveFile(file, uploadPath);
            ImageFile imageFile = ImageFile.builder()
                    .id(java.util.UUID.randomUUID().toString())
                    .originalName(file.getOriginalFilename())
                    .savedName(savedName)
                    .path(uploadPath)
                    .category("post")
                    .status(ImageStatus.TEMP)
                    .createdAt(LocalDateTime.now())
                    .build();

            ImageFile savedImageFile = imageFileRepository.saveAndFlush(imageFile);
            System.out.println("저장된 imageFile ID: " + savedImageFile.getId());

            PostImage postImage = PostImage.builder()
                    .id(req.getArticleId() + index)
                    .postId(req.getArticleId())
                    .sequence(index)
                    .imageFile(savedImageFile)
                    .createdAt(LocalDateTime.now())
                    .build();

            postImageRepository.saveAndFlush(postImage);

            uris.add(serverUrl + "/api/post-images/" + imageFile.getId());
            System.out.println(req.getArticleId());
            index++;
        }

        return PostImagesResponse.builder()
                .postId(req.getArticleId())
                .uris(uris)
                .build();
    }


    public List<ImageFile> getArticleImages(String postId) {
        List<PostImage> postImages = postImageRepository.findAllByPostIdOrderBySequenceAsc(postId);
        List<ImageFile> imageFiles = new ArrayList<>();
        for (PostImage image : postImages) {
            imageFiles.add(image.getImageFile());
        }

        return imageFiles;
    }
}

