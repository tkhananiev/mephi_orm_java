package com.example.ormplatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddModuleRequest(
        @NotNull Long courseId,
        @NotBlank String title,
        @NotNull Integer orderIndex,
        String description
) {}
