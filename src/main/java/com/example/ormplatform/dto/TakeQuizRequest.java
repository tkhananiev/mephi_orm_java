package com.example.ormplatform.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record TakeQuizRequest(
        @NotNull Long quizId,
        @NotNull Long studentId,
        Set<Long> chosenOptionIds
) {}
