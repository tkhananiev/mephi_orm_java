package com.example.ormplatform.repository;

import com.example.ormplatform.entity.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Override
    @EntityGraph(attributePaths = {"category", "teacher", "tags"})
    List<Course> findAll();

    @EntityGraph(attributePaths = {"category", "teacher", "tags", "modules", "modules.lessons"})
    Optional<Course> findWithStructureById(Long id);
}
