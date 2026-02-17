package com.example.ormplatform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="questions")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="quiz_id", nullable = false, foreignKey = @ForeignKey(name="fk_question_quiz"))
    private Quiz quiz;

    @Column(nullable = false, length = 2000)
    private String text;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<AnswerOption> options = new HashSet<>();
}
