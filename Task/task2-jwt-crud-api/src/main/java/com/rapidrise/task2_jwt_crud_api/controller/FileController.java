package com.rapidrise.task2_jwt_crud_api.controller;

import com.rapidrise.task2_jwt_crud_api.entity.User;
import com.rapidrise.task2_jwt_crud_api.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFiles(
            @RequestParam("files") MultipartFile[] files,
            @AuthenticationPrincipal User user
    ) throws IOException {

        List<Object> responses = new ArrayList<>();

        for (MultipartFile file : files) {
            responses.add(fileService.uploadFile(file, user).getBody());
        }

        return ResponseEntity.ok(responses);
    }
}
