package com.example.ormplatform.dto;

import jakarta.validation.constraints.NotNull;

public record LessonCompleteRequest(
        @NotNull Long lessonId,
        @NotNull Long studentId
) {}
