package com.mmajd.gobuy.admin.utils;

import com.mmajd.gobuy.common.constant.ASSETS_CONSTANTS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Slf4j
public class FileUploadUtil {
    public static void saveUserImage(String userDir, String filename, MultipartFile file) throws IOException {
        Path userPath = Path.of(ASSETS_CONSTANTS.USER_IMAGES_DIR.getValue(), userDir);
        saveFile(userPath, filename, file);
    }

    static void saveFile(Path savePath, String filename, MultipartFile file) throws IOException {
        if (!Files.exists(savePath)) {
            Files.createDirectories(savePath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = savePath.resolve(filename);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            log.info(ex.getMessage());
            throw new IOException("Could not save file: " + filename, ex);
        }
    }
}
