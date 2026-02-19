package com.example.ormplatform.api;

import com.example.ormplatform.dto.CourseStructureDto;
import com.example.ormplatform.dto.CreateLessonRequest;
import com.example.ormplatform.entity.Lesson;
import com.example.ormplatform.repository.LessonRepository;
import com.example.ormplatform.service.CourseService;
import com.example.ormplatform.service.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/modules")
@RequiredArgsConstructor
public class ModulesController {

    private final CourseService courseService;
    private final LessonRepository lessonRepository;

    @PostMapping("/{moduleId}/lessons")
    public CourseStructureDto.LessonDto addLesson(@PathVariable Long moduleId,
                                                  @Valid @RequestBody CreateLessonRequest request) {

        Lesson lesson = courseService.addLesson(
                moduleId,
                request.getTitle(),
                request.getContent(),
                request.getVideoUrl()
        );

        return new CourseStructureDto.LessonDto(
                lesson.getId(),
                lesson.getTitle(),
                null
        );
    }

    @DeleteMapping("/{moduleId}/lessons/{lessonId}")
    public void deleteLesson(@PathVariable Long moduleId, @PathVariable Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new NotFoundException("Lesson not found: " + lessonId));

        if (lesson.getCourseModule() == null || lesson.getCourseModule().getId() == null) {
            throw new NotFoundException("Lesson has no module: " + lessonId);
        }

        if (!lesson.getCourseModule().getId().equals(moduleId)) {
            throw new NotFoundException("Lesson " + lessonId + " not in module " + moduleId);
        }

        lessonRepository.delete(lesson);
    }
}
