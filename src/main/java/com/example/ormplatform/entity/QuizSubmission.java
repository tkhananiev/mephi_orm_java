package com.example.ormplatform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name="quiz_submissions",
       uniqueConstraints = @UniqueConstraint(name="uk_quiz_submission_quiz_student", columnNames = {"quiz_id","student_id"}))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class QuizSubmission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="quiz_id", nullable = false, foreignKey = @ForeignKey(name="fk_quiz_submission_quiz"))
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="student_id", nullable = false, foreignKey = @ForeignKey(name="fk_quiz_submission_student"))
    private User student;

    @Column(nullable = false)
    private Instant submittedAt;

    @Column(nullable = false)
    private Integer score;

    @PrePersist
    void prePersist() {
        if (submittedAt == null) submittedAt = Instant.now();
        if (score == null) score = 0;
    }
}
