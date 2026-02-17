package com.example.ormplatform.service;

import com.example.ormplatform.entity.*;
import com.example.ormplatform.repository.CourseRepository;
import com.example.ormplatform.repository.EnrollmentRepository;
import com.example.ormplatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Transactional
    public Enrollment enroll(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found: " + courseId));

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("User not found: " + studentId));

        if (student.getRole() != UserRole.STUDENT && student.getRole() != UserRole.ADMIN) {
            throw new ConflictException("User is not allowed to enroll as student: " + studentId);
        }

        enrollmentRepository.findByCourseIdAndStudentId(courseId, studentId).ifPresent(e -> {
            if (e.getStatus() == EnrollmentStatus.ACTIVE) {
                throw new ConflictException("Student already enrolled: course=" + courseId + ", student=" + studentId);
            }
        });

        return enrollmentRepository.save(Enrollment.builder()
                .course(course)
                .student(student)
                .status(EnrollmentStatus.ACTIVE)
                .build());
    }

    @Transactional
    public void cancel(Long courseId, Long studentId) {
        Enrollment enrollment = enrollmentRepository.findByCourseIdAndStudentId(courseId, studentId)
                .orElseThrow(() -> new NotFoundException("Enrollment not found: course=" + courseId + ", student=" + studentId));
        enrollment.setStatus(EnrollmentStatus.CANCELLED);
        enrollmentRepository.save(enrollment);
    }

    @Transactional(readOnly = true)
    public List<Enrollment> listByStudent(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }
}
