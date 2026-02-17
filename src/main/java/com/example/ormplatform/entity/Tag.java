package com.example.ormplatform.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tags", uniqueConstraints = @UniqueConstraint(name="uk_tag_name", columnNames = "name"))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Tag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;
}
