package com.example.ormplatform.service;

import com.example.ormplatform.dto.CreateQuizRequest;
import com.example.ormplatform.entity.*;
import com.example.ormplatform.repository.CourseModuleRepository;
import com.example.ormplatform.repository.QuizRepository;
import com.example.ormplatform.repository.QuizSubmissionRepository;
import com.example.ormplatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizSubmissionRepository quizSubmissionRepository;
    private final CourseModuleRepository courseModuleRepository;
    private final UserRepository userRepository;

    @Transactional
    public Quiz createQuiz(CreateQuizRequest req) {
        CourseModule module = courseModuleRepository.findById(req.moduleId())
                .orElseThrow(() -> new NotFoundException("Module not found: " + req.moduleId()));

        quizRepository.findByCourseModuleId(req.moduleId()).ifPresent(q -> {
            throw new ConflictException("Quiz already exists for module: " + req.moduleId());
        });

        Quiz quiz = Quiz.builder()
                .courseModule(module)
                .title(req.title())
                .build();

        if (req.questions() != null) {
            Set<Question> questions = new HashSet<>();
            for (CreateQuizRequest.QuestionCreate q : req.questions()) {
                Question question = Question.builder()
                        .quiz(quiz)
                        .text(q.text())
                        .build();

                Set<AnswerOption> options = new HashSet<>();
                if (q.options() != null) {
                    for (CreateQuizRequest.OptionCreate o : q.options()) {
                        options.add(AnswerOption.builder()
                                .question(question)
                                .text(o.text())
                                .correct(o.correct())
                                .build());
                    }
                }
                question.setOptions(options);
                questions.add(question);
            }
            quiz.setQuestions(questions);
        }

        return quizRepository.save(quiz);
    }

    @Transactional
    public QuizSubmission takeQuiz(Long quizId, Long studentId, Set<Long> chosenOptionIds) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new NotFoundException("Quiz not found: " + quizId));

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("User not found: " + studentId));

        if (student.getRole() != UserRole.STUDENT && student.getRole() != UserRole.ADMIN) {
            throw new ConflictException("User is not allowed to take quiz: " + studentId);
        }

        quizSubmissionRepository.findByQuizIdAndStudentId(quizId, studentId).ifPresent(s -> {
            throw new ConflictException("Quiz already taken: quiz=" + quizId + ", student=" + studentId);
        });

        Set<Long> chosen = chosenOptionIds == null ? Set.of() : chosenOptionIds;

        Map<Long, Boolean> optionCorrect = new HashMap<>();
        for (Question q : quiz.getQuestions()) {
            for (AnswerOption o : q.getOptions()) {
                optionCorrect.put(o.getId(), o.isCorrect());
            }
        }

        int score = 0;
        for (Long id : chosen) {
            if (Boolean.TRUE.equals(optionCorrect.get(id))) score++;
        }

        return quizSubmissionRepository.save(QuizSubmission.builder()
                .quiz(quiz)
                .student(student)
                .score(score)
                .build());
    }

    @Transactional(readOnly = true)
    public Quiz getQuiz(Long id) {
        return quizRepository.findById(id).orElseThrow(() -> new NotFoundException("Quiz not found: " + id));
    }
}
