package com.example.ormplatform.dto;

import jakarta.validation.constraints.NotNull;

public record EnrollRequest(@NotNull Long courseId, @NotNull Long studentId) {}
