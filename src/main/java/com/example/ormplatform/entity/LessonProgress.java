package com.example.ormplatform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
        name = "lesson_progress",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_progress_lesson_student",
                columnNames = {"lesson_id", "student_id"}
        )
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LessonProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id", nullable = false, foreignKey = @ForeignKey(name = "fk_progress_lesson"))
    private Lesson lesson;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false, foreignKey = @ForeignKey(name = "fk_progress_student"))
    private User student;

    @Column(nullable = false)
    private Instant completedAt;

    @PrePersist
    void prePersist() {
        if (completedAt == null) completedAt = Instant.now();
    }
}
