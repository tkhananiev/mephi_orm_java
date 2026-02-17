package com.example.ormplatform.repository;

import com.example.ormplatform.entity.QuizSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission, Long> {
    Optional<QuizSubmission> findByQuizIdAndStudentId(Long quizId, Long studentId);
}
