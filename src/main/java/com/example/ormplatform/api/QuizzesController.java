package com.example.ormplatform.api;

import com.example.ormplatform.dto.CreateQuizRequest;
import com.example.ormplatform.dto.TakeQuizRequest;
import com.example.ormplatform.entity.Quiz;
import com.example.ormplatform.entity.QuizSubmission;
import com.example.ormplatform.service.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quizzes")
public class QuizzesController {

    private final QuizService quizService;

    @PostMapping
    public Quiz create(@Valid @RequestBody CreateQuizRequest req) {
        return quizService.createQuiz(req);
    }

    @PostMapping("/take")
    public QuizSubmission take(@Valid @RequestBody TakeQuizRequest req) {
        return quizService.takeQuiz(req.quizId(), req.studentId(), req.chosenOptionIds());
    }

    @GetMapping("/{id}")
    public Quiz get(@PathVariable Long id) {
        return quizService.getQuiz(id);
    }
}
