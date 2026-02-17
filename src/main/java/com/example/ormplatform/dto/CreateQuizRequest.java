package com.example.ormplatform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateQuizRequest(
        @NotNull Long moduleId,
        @NotBlank String title,
        List<QuestionCreate> questions
) {
    public record QuestionCreate(
            @NotBlank String text,
            List<OptionCreate> options
    ) {}
    public record OptionCreate(
            @NotBlank String text,
            boolean correct
    ) {}
}
