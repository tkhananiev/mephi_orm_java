package com.example.ormplatform.repository;

import com.example.ormplatform.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByCourseModuleId(Long moduleId);
}
