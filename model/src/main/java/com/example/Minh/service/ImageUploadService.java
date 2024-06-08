package com.example.Minh.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageUploadService {

    // public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";

    // public String uploadImage(MultipartFile file) throws IOException {
    //     String originalFilename = file.getOriginalFilename();
    //     Path fileNameAndPath = Paths.get(uploadDirectory, originalFilename);
    //     Files.write(fileNameAndPath, file.getBytes());
    //     return originalFilename;
    // }
}
