package dding.fileupload.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "profile_image")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
public class ProfileImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId; // 인증 모듈에서 내려주는 유저 ID (loginId 또는 uuid)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private ImageFile imageFile;

    private boolean isCurrent;

    private LocalDateTime createdAt;


}
