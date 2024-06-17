package ru.atomskills.back.utils;

import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FilesUtil {

    private final static String BASE_DIR = "files";

    public static void saveFile(String path, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(BASE_DIR, path);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }
    }

    public static Resource getFile(String path, String fileName) {
        return new PathResource(Paths.get(BASE_DIR, path, fileName));
    }
}
