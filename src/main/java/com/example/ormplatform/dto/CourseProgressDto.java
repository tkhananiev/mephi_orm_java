package com.example.ormplatform.dto;

public record CourseProgressDto(
        Long courseId,
        Long studentId,
        long totalLessons,
        long completedLessons,
        int completionPercent
) {}
