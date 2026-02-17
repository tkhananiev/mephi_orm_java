package com.example.ormplatform.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="assignments")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Assignment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="module_id", nullable = false, foreignKey = @ForeignKey(name="fk_assignment_module"))
    private CourseModule courseModule;

    @Column(nullable = false, length = 300)
    private String title;

    @Column(length = 8000)
    private String description;

    @Column(nullable = false)
    private Integer maxScore;
}
