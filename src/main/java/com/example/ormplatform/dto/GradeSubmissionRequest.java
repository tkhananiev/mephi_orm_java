package com.example.ormplatform.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record GradeSubmissionRequest(
        @NotNull Long submissionId,
        @NotNull @Min(0) @Max(1000) Integer score
) {}
