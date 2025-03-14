package dding.fileupload.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    public String saveFile(MultipartFile file, String uploadDir) throws IOException {
        String originalName = file.getOriginalFilename();
        String ext = originalName.substring(originalName.lastIndexOf('.') + 1);
        String uuid = UUID.randomUUID().toString();
        String savedName = uuid + "." + ext;

        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        File savedFile = new File(uploadDir, savedName);
        file.transferTo(savedFile);

        return savedName;
    }

    public boolean deleteFile(String fullPath) {
        File file = new File(fullPath);
        return file.exists() && file.delete();
    }

    public String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }
}
