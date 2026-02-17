package com.example.ormplatform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    private String title;

    @Column(length = 5000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="category_id", nullable = false, foreignKey = @ForeignKey(name="fk_course_category"))
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="teacher_id", nullable = false, foreignKey = @ForeignKey(name="fk_course_teacher"))
    private User teacher;

    private Integer durationHours;

    private LocalDate startDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="course_tag",
            joinColumns = @JoinColumn(name="course_id", foreignKey = @ForeignKey(name="fk_course_tag_course")),
            inverseJoinColumns = @JoinColumn(name="tag_id", foreignKey = @ForeignKey(name="fk_course_tag_tag"))
    )
    @Builder.Default
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("orderIndex ASC")
    @Builder.Default
    private Set<CourseModule> modules = new HashSet<>();
}
