package com.example.ormplatform.api;

import com.example.ormplatform.dto.EnrollRequest;
import com.example.ormplatform.dto.EnrollmentDto;
import com.example.ormplatform.entity.Enrollment;
import com.example.ormplatform.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/enrollments")
public class EnrollmentsController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnrollmentDto enroll(@Valid @RequestBody EnrollRequest req) {
        Enrollment e = enrollmentService.enroll(req.courseId(), req.studentId());
        return toDto(e);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@RequestParam Long courseId, @RequestParam Long studentId) {
        enrollmentService.cancel(courseId, studentId);
    }

    @GetMapping
    public List<EnrollmentDto> listByStudent(@RequestParam Long studentId) {
        return enrollmentService.listByStudent(studentId).stream()
                .map(this::toDto)
                .toList();
    }

    private EnrollmentDto toDto(Enrollment e) {
        Long courseId = (e.getCourse() == null) ? null : e.getCourse().getId();
        Long studentId = (e.getStudent() == null) ? null : e.getStudent().getId();

        return new EnrollmentDto(
                e.getId(),
                courseId,
                studentId,
                e.getStatus() == null ? null : e.getStatus().name(),
                e.getEnrolledAt()
        );
    }
}
