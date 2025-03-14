package dding.fileupload.repository;




import dding.fileupload.entity.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageFileRepository extends JpaRepository<ImageFile, String> {
}