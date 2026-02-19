package com.example.ormplatform.api;

import com.example.ormplatform.dto.LessonCompleteRequest;
import com.example.ormplatform.dto.LessonProgressDto;
import com.example.ormplatform.entity.Lesson;
import com.example.ormplatform.entity.LessonProgress;
import com.example.ormplatform.entity.User;
import com.example.ormplatform.entity.UserRole;
import com.example.ormplatform.repository.LessonProgressRepository;
import com.example.ormplatform.repository.LessonRepository;
import com.example.ormplatform.repository.UserRepository;
import com.example.ormplatform.service.ConflictException;
import com.example.ormplatform.service.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final LessonProgressRepository lessonProgressRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    @PostMapping("/complete")
    @ResponseStatus(HttpStatus.CREATED)
    public LessonProgressDto complete(@Valid @RequestBody LessonCompleteRequest req) {

        Lesson lesson = lessonRepository.findById(req.lessonId())
                .orElseThrow(() -> new NotFoundException("Lesson not found: " + req.lessonId()));

        User student = userRepository.findById(req.studentId())
                .orElseThrow(() -> new NotFoundException("Student not found: " + req.studentId()));

        if (student.getRole() != UserRole.STUDENT) {
            throw new ConflictException("User is not allowed to complete lesson as student: " + req.studentId());
        }

        boolean exists = lessonProgressRepository.findByLessonIdAndStudentId(req.lessonId(), req.studentId()).isPresent();
        if (exists) {
            throw new ConflictException("Lesson already completed: lesson=" + req.lessonId() + ", student=" + req.studentId());
        }

        LessonProgress saved = lessonProgressRepository.save(
                LessonProgress.builder()
                        .lesson(lesson)
                        .student(student)
                        .build()
        );

        return new LessonProgressDto(
                saved.getId(),
                saved.getLesson().getId(),
                saved.getStudent().getId(),
                saved.getCompletedAt()
        );
    }
}

