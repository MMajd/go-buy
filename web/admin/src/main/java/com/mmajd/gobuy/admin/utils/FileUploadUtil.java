package com.mmajd.gobuy.admin.utils;

import com.mmajd.gobuy.common.constant.ASSETS_CONSTANTS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Slf4j
public class FileUploadUtil {
    public static void saveUserImage(String userDir, String filename, MultipartFile file) throws IOException {
        Path userPath = Path.of(ASSETS_CONSTANTS.USER_IMAGES_DIR.getValue(), userDir);
        saveFile(userPath, filename, file, true);
    }

    static void saveFile(Path savePath, String filename, MultipartFile file, boolean cleanPath) throws IOException {
        if (cleanPath) {
            cleanDir(savePath);
        }

        saveFile(savePath, filename, file);
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

    public static void cleanDir(String dir) {
        cleanDir(Path.of(StringUtils.cleanPath(dir)));
    }

    public static void cleanDir(Path dir) {
        try (Stream<Path> files = Files.list(dir)) {

            files.forEach(f -> {
                if (!Files.isDirectory(f)) {
                    try {
                        Files.deleteIfExists(f);
                    } catch (IOException ex) {
                        log.warn("Could not delete directory" + f.getFileName());
                    }
                }
            });

        } catch (IOException ex) {
            log.warn("Could not list directory" + dir.toFile().getAbsolutePath());
        }
    }
}
