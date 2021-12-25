package com.services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public class ImageStorageService implements IStorageService {
    private final Path storageFolder = Paths.get("uploads");
    // constructor
    public ImageStorageService() {
        try {
            Files.createDirectories(storageFolder);
        }catch (IOException exception) {
            throw new RuntimeException("Can't initialize storage", exception);
        }
    }
    private boolean isImageFile(MultipartFile file) {
        // let install FileNameUtils
        String fileNameExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        assert fileNameExtension != null;
        return Arrays.asList(new String[] { "png", "jpg", "jpeg", "bmp", "jfif"})
                 .contains(fileNameExtension.trim().toLowerCase());
    }
    @Override
    public String storeFile(MultipartFile file) {
        try {
            System.out.println("haha");
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }
            // check file is image ?
            if (!isImageFile(file)) {
                throw new RuntimeException("You can only upload image file");
            }
            // file must be <= 5mb
            float fileSizeInMegabytes = file.getSize() / 1_000_000.0f;
            if (fileSizeInMegabytes > 5.0f) {
                throw new RuntimeException("File must be <= 5Mb");
            }
            // file must be re name, Why ?
            String fileNameExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            assert fileNameExtension != null;
            String generatedFileName = UUID.randomUUID().toString().replace("-", "");
            generatedFileName = generatedFileName + "." + fileNameExtension;
            Path destinationPath = this.storageFolder
                    .resolve(Paths.get(generatedFileName)).normalize().toAbsolutePath();
            if (!destinationPath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                throw new RuntimeException("Can't store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            }
            return generatedFileName;
        } catch (IOException exception) {
            return null;
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.storageFolder, 1)
                    .filter(path -> !path.equals(this.storageFolder))
                    .map(this.storageFolder::relativize);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to load stored files", exception);
        }
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try {
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return StreamUtils.copyToByteArray(resource.getInputStream());
            } else {
                throw new RuntimeException("Could not read file: " + fileName);
            }
        } catch (IOException exception) {
            throw new RuntimeException("Could not read file: " + fileName, exception);
        }
    }

    @Override
    public void deleteAllFiles() {

    }
}
