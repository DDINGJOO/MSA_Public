package dding.fileupload.entity;


import dding.fileupload.config.ImageStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name= "image_file")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageFile {

    @Id
    private String id; // UUID

    private String originalName;
    private String savedName;
    private String path;

    private String category;

    @Enumerated(EnumType.STRING)
    private ImageStatus status;

    private LocalDateTime createdAt;
}
