package com.example.ormplatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubmitAssignmentRequest(
        @NotNull Long assignmentId,
        @NotNull Long studentId,
        @NotBlank String content
) {}
