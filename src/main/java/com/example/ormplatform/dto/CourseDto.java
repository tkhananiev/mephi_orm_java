package com.example.ormplatform.dto;

import java.time.LocalDate;
import java.util.Set;

public record CourseDto(
        Long id,
        String title,
        String description,
        Long categoryId,
        Long teacherId,
        Integer durationHours,
        LocalDate startDate,
        Set<String> tags
) {}
