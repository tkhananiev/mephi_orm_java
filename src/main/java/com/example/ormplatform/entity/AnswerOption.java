package com.example.ormplatform.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="answer_options")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AnswerOption {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="question_id", nullable = false, foreignKey = @ForeignKey(name="fk_option_question"))
    private Question question;

    @Column(nullable = false, length = 2000)
    private String text;

    @Column(nullable = false)
    private boolean correct;
}
