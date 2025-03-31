package com.language.service.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtils {

    private static Logger log = LoggerFactory.getLogger(FileUtils.class);

    private FileUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Boolean copyAttachFile(MultipartFile file, String attachFileDir, String fileName) throws IOException {
        File dir = new File(attachFileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Path targetLocation = Paths.get(attachFileDir + File.separator + fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        log.debug("File copied successful: {}{}{}", attachFileDir, File.separator, fileName);
        return true;
    }

    /**
     * Deletes a file or a directory.
     *
     * @param file the file or directory which should be deleted.
     * @return true when the file could be deleted
     */
    public static boolean delete(File file) {
        if (file.isDirectory()) {
            String[] children = file.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = delete(new File(file, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return file.delete();
    }

    public static String getFileExtension(String filename) {
        if(filename == null || filename.trim().isEmpty() || "".equalsIgnoreCase(filename)){
            return "";
        }
        int lastIndexOfDot = filename.lastIndexOf(".");
        if (lastIndexOfDot > 0 && lastIndexOfDot < filename.length() - 1) {
            return filename.substring(lastIndexOfDot + 1).toLowerCase();
        } else {
            return "";
        }
    }
}
