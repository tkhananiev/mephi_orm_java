package com.example.ormplatform.repository;

import com.example.ormplatform.entity.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {

    Optional<LessonProgress> findByLessonIdAndStudentId(Long lessonId, Long studentId);

    // Lesson -> courseModule -> course
    long countByStudentIdAndLesson_CourseModule_Course_Id(Long studentId, Long courseId);
}
