package dding.fileupload.repository;


import dding.fileupload.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
    List<ProfileImage> findByUserIdOrderByCreatedAtDesc(String userId);
    Optional<ProfileImage> findByUserIdAndIsCurrentTrue(String userId);
}
