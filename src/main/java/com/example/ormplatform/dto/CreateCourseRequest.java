package com.example.ormplatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public record CreateCourseRequest(
        @NotBlank String title,
        String description,
        @NotNull Long categoryId,
        @NotNull Long teacherId,
        Integer durationHours,
        LocalDate startDate,
        Set<String> tags
) {}
