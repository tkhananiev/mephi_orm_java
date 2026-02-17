package com.example.ormplatform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "course_modules",
       uniqueConstraints = @UniqueConstraint(name="uk_module_course_order", columnNames = {"course_id","order_index"}))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CourseModule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="course_id", nullable = false, foreignKey = @ForeignKey(name="fk_module_course"))
    private Course course;

    @Column(nullable = false, length = 300)
    private String title;

    @Column(name="order_index", nullable = false)
    private Integer orderIndex;

    @Column(length = 5000)
    private String description;

    @OneToMany(mappedBy = "courseModule", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Lesson> lessons = new HashSet<>();

    @OneToMany(mappedBy = "courseModule", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Assignment> assignments = new HashSet<>();

    @OneToOne(mappedBy = "courseModule", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Quiz quiz;
}
