package com.example.ormplatform.api;

import com.example.ormplatform.dto.*;
import com.example.ormplatform.entity.Assignment;
import com.example.ormplatform.entity.Submission;
import com.example.ormplatform.service.AssignmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assignments")
public class AssignmentsController {

    private final AssignmentService assignmentService;

    @PostMapping
    public Assignment create(@Valid @RequestBody CreateAssignmentRequest req) {
        return assignmentService.createAssignment(req.moduleId(), req.title(), req.description(), req.maxScore());
    }

    @PostMapping("/submit")
    public Submission submit(@Valid @RequestBody SubmitAssignmentRequest req) {
        return assignmentService.submit(req.assignmentId(), req.studentId(), req.content());
    }

    @PostMapping("/grade")
    public Submission grade(@Valid @RequestBody GradeSubmissionRequest req) {
        return assignmentService.grade(req.submissionId(), req.score());
    }

    @GetMapping("/submissions")
    public List<Submission> list(@RequestParam Long assignmentId) {
        return assignmentService.listSubmissions(assignmentId);
    }
}
