package com.example.ormplatform.dto;

import java.time.Instant;

public record EnrollmentDto(
        Long id,
        Long courseId,
        Long studentId,
        String status,
        Instant enrolledAt
) {}

