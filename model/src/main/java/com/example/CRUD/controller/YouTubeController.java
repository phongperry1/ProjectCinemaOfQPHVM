package com.example.CRUD.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class YouTubeController {

    @GetMapping("/youtube-video")
    public String getYouTubeVideoId(@RequestParam("url") String url) {
        String videoId = extractVideoId(url);
        return videoId;
    }

    private String extractVideoId(String url) {
        String videoId = null;
        if (url != null && url.trim().length() > 0) {
            if (url.contains("youtube.com") || url.contains("youtu.be")) {
                String[] parts = url.split("v=|youtu.be/");
                if (parts.length >= 2) {
                    videoId = parts[1].substring(0, 11);
                }
            }
        }
        return videoId;
    }
}
