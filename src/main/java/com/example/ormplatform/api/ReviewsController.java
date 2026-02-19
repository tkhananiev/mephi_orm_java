package com.example.ormplatform.api;

import com.example.ormplatform.dto.CreateReviewRequest;
import com.example.ormplatform.dto.ReviewDto;
import com.example.ormplatform.entity.Course;
import com.example.ormplatform.entity.CourseReview;
import com.example.ormplatform.entity.User;
import com.example.ormplatform.entity.UserRole;
import com.example.ormplatform.repository.CourseRepository;
import com.example.ormplatform.repository.CourseReviewRepository;
import com.example.ormplatform.repository.UserRepository;
import com.example.ormplatform.service.ConflictException;
import com.example.ormplatform.service.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewsController {

    private final CourseReviewRepository courseReviewRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDto create(@Valid @RequestBody CreateReviewRequest req) {
        Course course = courseRepository.findById(req.courseId())
                .orElseThrow(() -> new NotFoundException("Course not found: " + req.courseId()));

        User student = userRepository.findById(req.studentId())
                .orElseThrow(() -> new NotFoundException("Student not found: " + req.studentId()));

        if (student.getRole() != UserRole.STUDENT) {
            throw new ConflictException("User is not allowed to review as student: " + req.studentId());
        }

        CourseReview review = CourseReview.builder()
                .course(course)
                .student(student)
                .rating(req.rating())
                .comment(req.comment())
                .build();

        try {
            CourseReview saved = courseReviewRepository.save(review);
            return toDto(saved);
        } catch (DataIntegrityViolationException e) {
            // uk_review_course_student (course_id, student_id)
            throw new ConflictException("Review already exists: course=" + req.courseId() + ", student=" + req.studentId());
        }
    }

    @GetMapping
    public List<ReviewDto> listByCourse(@RequestParam Long courseId) {
        // репозиторий пока без findByCourseId — фильтруем в контроллере, чтобы не плодить классы в этом шаге
        return courseReviewRepository.findAll().stream()
                .filter(r -> r.getCourse() != null && r.getCourse().getId() != null && r.getCourse().getId().equals(courseId))
                .map(this::toDto)
                .toList();
    }

    private ReviewDto toDto(CourseReview r) {
        Long courseId = (r.getCourse() == null) ? null : r.getCourse().getId();
        Long studentId = (r.getStudent() == null) ? null : r.getStudent().getId();

        return new ReviewDto(
                r.getId(),
                courseId,
                studentId,
                r.getRating() == null ? 0 : r.getRating(),
                r.getComment(),
                r.getCreatedAt()
        );
    }
}


