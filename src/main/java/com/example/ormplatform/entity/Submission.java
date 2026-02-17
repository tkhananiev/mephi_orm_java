package com.example.ormplatform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name="submissions",
       uniqueConstraints = @UniqueConstraint(name="uk_submission_assignment_student", columnNames = {"assignment_id","student_id"}))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Submission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="assignment_id", nullable = false, foreignKey = @ForeignKey(name="fk_submission_assignment"))
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="student_id", nullable = false, foreignKey = @ForeignKey(name="fk_submission_student"))
    private User student;

    @Column(nullable = false)
    private Instant submittedAt;

    private Integer score;

    @Column(length = 8000)
    private String content;

    @PrePersist
    void prePersist() {
        if (submittedAt == null) submittedAt = Instant.now();
    }
}
