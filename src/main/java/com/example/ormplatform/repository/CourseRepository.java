package com.example.ormplatform.repository;

import com.example.ormplatform.entity.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    interface CourseSummaryView {
        Long getId();
        String getTitle();
        String getDescription();
        Integer getDurationHours();
        LocalDate getStartDate();
        CategoryView getCategory();
        UserView getTeacher();

        interface CategoryView { Long getId(); }
        interface UserView { Long getId(); }
    }

    List<CourseSummaryView> findAllProjectedBy();

    Optional<CourseSummaryView> findProjectedById(Long id);

    @EntityGraph(attributePaths = {"modules", "modules.lessons"})
    Optional<Course> findWithStructureById(Long id);
}
