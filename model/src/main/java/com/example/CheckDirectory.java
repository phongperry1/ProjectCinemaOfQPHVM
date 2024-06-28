package com.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class CheckDirectory {
    public static void main(String[] args) {
        Path path = Paths.get("theater-photo");
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                System.out.println("Directory created");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Directory exists");
        }
    }
}