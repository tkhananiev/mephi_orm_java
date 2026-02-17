package com.example.ormplatform.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lessons")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Lesson {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="module_id", nullable = false, foreignKey = @ForeignKey(name="fk_lesson_module"))
    private CourseModule courseModule;

    @Column(nullable = false, length = 300)
    private String title;

    @Column(length = 10000)
    private String content;

    @Column(length = 1000)
    private String videoUrl;
}
