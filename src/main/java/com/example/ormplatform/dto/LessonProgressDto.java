package com.example.ormplatform.dto;

import java.time.Instant;

public record LessonProgressDto(
        Long id,
        Long lessonId,
        Long studentId,
        Instant completedAt
) {}
