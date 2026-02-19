package com.example.ormplatform.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record CourseStructureDto(
        Long id,
        String title,
        String description,
        Long categoryId,
        Long teacherId,
        Integer durationHours,
        LocalDate startDate,
        Set<String> tags,
        List<ModuleDto> modules
) {
    public record ModuleDto(
            Long id,
            String title,
            Integer orderIndex,
            List<LessonDto> lessons
    ) {}

    public record LessonDto(
            Long id,
            String title,
            Integer orderIndex
    ) {}
}
