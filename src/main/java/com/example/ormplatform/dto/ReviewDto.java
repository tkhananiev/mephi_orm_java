package com.example.ormplatform.dto;

import java.time.Instant;

public record ReviewDto(
        Long id,
        Long courseId,
        Long studentId,
        int rating,
        String comment,
        Instant createdAt
) {}

