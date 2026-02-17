package com.example.ormplatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddLessonRequest(
        @NotNull Long moduleId,
        @NotBlank String title,
        String content,
        String videoUrl
) {}
