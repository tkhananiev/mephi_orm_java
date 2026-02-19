package com.example.ormplatform.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateLessonRequest {

    @NotBlank
    private String title;

    private String content;

    private String videoUrl;

    public CreateLessonRequest() {
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
