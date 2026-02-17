package com.example.ormplatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateAssignmentRequest(
        @NotNull Long moduleId,
        @NotBlank String title,
        String description,
        @NotNull Integer maxScore
) {}
