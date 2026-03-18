package com.rapidrise.task2_jwt_crud_api.service;

import com.rapidrise.task2_jwt_crud_api.dto.ResponseStructure;
import com.rapidrise.task2_jwt_crud_api.entity.File;
import com.rapidrise.task2_jwt_crud_api.entity.User;
import com.rapidrise.task2_jwt_crud_api.exception.FileLimitExceededException;
import com.rapidrise.task2_jwt_crud_api.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    private final String uploadDir = "uploads/";

    // ✅ Upload file
    public ResponseEntity<ResponseStructure<File>> uploadFile(MultipartFile file, User user) throws IOException {

        // 🔥 File size check (100MB)
        if (file.getSize() > 100 * 1024 * 1024) {
            throw new FileLimitExceededException("File size exceeds 100MB");
        }

        // 🔥 Total storage check (1GB)
        long totalSize = fileRepository.findByUser(user, Pageable.unpaged())
                .getContent()
                .stream()
                .mapToLong(File::getFileSize)
                .sum();

        if (totalSize + file.getSize() > 1_000_000_000) {
            throw new FileLimitExceededException("Storage limit exceeded (1GB)");
        }

        // Create folder if not exists
        Path pathDir = Paths.get(uploadDir);
        if (!Files.exists(pathDir)) {
            Files.createDirectories(pathDir);
        }

        // Save file
        String filePath = uploadDir + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        File newFile = File.builder()
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .filePath(filePath)
                .uploadedAt(LocalDateTime.now())
                .user(user)
                .build();

        File savedFile = fileRepository.save(newFile);

        // 🔥 ResponseStructure
        ResponseStructure<File> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("File uploaded successfully");
        response.setData(savedFile);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
