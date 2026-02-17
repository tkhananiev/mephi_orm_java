package com.example.ormplatform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name="enrollments",
       uniqueConstraints = @UniqueConstraint(name="uk_enrollment_course_student", columnNames = {"course_id","student_id"}))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Enrollment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="course_id", nullable = false, foreignKey = @ForeignKey(name="fk_enrollment_course"))
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="student_id", nullable = false, foreignKey = @ForeignKey(name="fk_enrollment_student"))
    private User student;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EnrollmentStatus status;

    @Column(nullable = false)
    private Instant enrolledAt;

    @PrePersist
    void prePersist() {
        if (status == null) status = EnrollmentStatus.ACTIVE;
        if (enrolledAt == null) enrolledAt = Instant.now();
    }
}
