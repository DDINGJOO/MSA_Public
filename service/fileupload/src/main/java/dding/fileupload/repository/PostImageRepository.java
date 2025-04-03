package dding.fileupload.repository;


import dding.fileupload.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, String> {

    List<PostImage> findAllByPostIdOrderBySequenceAsc(String postId);


    boolean existsByPostId(String articleId);
}
