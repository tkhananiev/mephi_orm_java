package com.example.ormplatform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name="course_reviews",
       uniqueConstraints = @UniqueConstraint(name="uk_review_course_student", columnNames = {"course_id","student_id"}))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CourseReview {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="course_id", nullable = false, foreignKey = @ForeignKey(name="fk_review_course"))
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="student_id", nullable = false, foreignKey = @ForeignKey(name="fk_review_student"))
    private User student;

    @Column(nullable = false)
    private Integer rating;

    @Column(length = 4000)
    private String comment;

    @Column(nullable = false)
    private Instant createdAt;

    @PrePersist
    void prePersist() {
        if (createdAt == null) createdAt = Instant.now();
    }
}
