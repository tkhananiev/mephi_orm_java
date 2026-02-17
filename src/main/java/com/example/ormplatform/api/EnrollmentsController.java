package com.example.ormplatform.api;

import com.example.ormplatform.dto.EnrollRequest;
import com.example.ormplatform.entity.Enrollment;
import com.example.ormplatform.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/enrollments")
public class EnrollmentsController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public Enrollment enroll(@Valid @RequestBody EnrollRequest req) {
        return enrollmentService.enroll(req.courseId(), req.studentId());
    }

    @DeleteMapping
    public void cancel(@RequestParam Long courseId, @RequestParam Long studentId) {
        enrollmentService.cancel(courseId, studentId);
    }

    @GetMapping
    public List<Enrollment> listByStudent(@RequestParam Long studentId) {
        return enrollmentService.listByStudent(studentId);
    }
}
