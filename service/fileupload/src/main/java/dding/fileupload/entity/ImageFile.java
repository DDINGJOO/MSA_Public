package dding.fileupload.entity;


import dding.fileupload.config.ImageStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name ="images")
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
