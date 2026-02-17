package com.example.ormplatform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="quizzes")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Quiz {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="module_id", nullable = false, unique = true, foreignKey = @ForeignKey(name="fk_quiz_module"))
    private CourseModule courseModule;

    @Column(nullable = false, length = 300)
    private String title;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Question> questions = new HashSet<>();
}
