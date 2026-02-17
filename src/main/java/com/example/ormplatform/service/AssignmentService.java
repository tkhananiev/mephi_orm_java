package com.example.ormplatform.service;

import com.example.ormplatform.entity.*;
import com.example.ormplatform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final SubmissionRepository submissionRepository;
    private final CourseModuleRepository courseModuleRepository;
    private final UserRepository userRepository;

    @Transactional
    public Assignment createAssignment(Long moduleId, String title, String description, Integer maxScore) {
        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new NotFoundException("Module not found: " + moduleId));

        return assignmentRepository.save(Assignment.builder()
                .courseModule(module)
                .title(title)
                .description(description)
                .maxScore(maxScore)
                .build());
    }

    @Transactional
    public Submission submit(Long assignmentId, Long studentId, String content) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new NotFoundException("Assignment not found: " + assignmentId));

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("User not found: " + studentId));

        if (student.getRole() != UserRole.STUDENT && student.getRole() != UserRole.ADMIN) {
            throw new ConflictException("User is not allowed to submit: " + studentId);
        }

        submissionRepository.findByAssignmentIdAndStudentId(assignmentId, studentId).ifPresent(s -> {
            throw new ConflictException("Submission already exists: assignment=" + assignmentId + ", student=" + studentId);
        });

        return submissionRepository.save(Submission.builder()
                .assignment(assignment)
                .student(student)
                .content(content)
                .build());
    }

    @Transactional
    public Submission grade(Long submissionId, Integer score) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new NotFoundException("Submission not found: " + submissionId));
        submission.setScore(score);
        return submissionRepository.save(submission);
    }

    @Transactional(readOnly = true)
    public List<Submission> listSubmissions(Long assignmentId) {
        return submissionRepository.findByAssignmentId(assignmentId);
    }
}
