package com.example.ormplatform.api;

import com.example.ormplatform.dto.CourseDto;
import com.example.ormplatform.dto.CourseStructureDto;
import com.example.ormplatform.dto.CreateCourseRequest;
import com.example.ormplatform.dto.CreateModuleRequest;
import com.example.ormplatform.entity.Course;
import com.example.ormplatform.entity.CourseModule;
import com.example.ormplatform.entity.Lesson;
import com.example.ormplatform.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CoursesController {

    private final CourseService courseService;

    @GetMapping
    public List<CourseDto> list() {
        return courseService.listCourses().stream()
                .map(this::toCourseDto)
                .toList();
    }

    @GetMapping("/{id}")
    public CourseDto get(@PathVariable Long id) {
        return toCourseDto(courseService.getCourse(id));
    }

    @GetMapping("/{id}/structure")
    public CourseStructureDto getWithStructure(@PathVariable Long id) {
        return toStructureDto(courseService.getCourseWithStructure(id));
    }

    @PostMapping
    public CourseDto create(@Valid @RequestBody CreateCourseRequest request) {
        Course created = courseService.createCourse(
                request.getTitle(),
                request.getDescription(),
                request.getCategoryId(),
                request.getTeacherId(),
                request.getDurationHours(),
                request.getStartDate(),
                request.getTags()
        );
        return toCourseDto(created);
    }

    /**
     * Новый endpoint:
     * POST /api/courses/{courseId}/modules
     */
    @PostMapping("/{courseId}/modules")
    public CourseStructureDto addModule(@PathVariable Long courseId,
                                        @Valid @RequestBody CreateModuleRequest request) {

        courseService.addModule(
                courseId,
                request.getTitle(),
                request.getOrderIndex(),
                request.getDescription()
        );

        return toStructureDto(courseService.getCourseWithStructure(courseId));
    }

    private CourseDto toCourseDto(Course c) {
        Set<String> tags = (c.getTags() == null)
                ? Set.of()
                : c.getTags().stream().map(t -> t.getName()).collect(Collectors.toSet());

        return new CourseDto(
                c.getId(),
                c.getTitle(),
                c.getDescription(),
                c.getCategory() == null ? null : c.getCategory().getId(),
                c.getTeacher() == null ? null : c.getTeacher().getId(),
                c.getDurationHours(),
                c.getStartDate(),
                tags
        );
    }

    private CourseStructureDto toStructureDto(Course c) {
        Set<String> tags = (c.getTags() == null)
                ? Set.of()
                : c.getTags().stream().map(t -> t.getName()).collect(Collectors.toSet());

        List<CourseStructureDto.ModuleDto> modules = (c.getModules() == null)
                ? List.of()
                : c.getModules().stream()
                .sorted(Comparator.comparingInt(m -> m.getOrderIndex() == null ? 0 : m.getOrderIndex()))
                .map(this::toModuleDto)
                .toList();

        return new CourseStructureDto(
                c.getId(),
                c.getTitle(),
                c.getDescription(),
                c.getCategory() == null ? null : c.getCategory().getId(),
                c.getTeacher() == null ? null : c.getTeacher().getId(),
                c.getDurationHours(),
                c.getStartDate(),
                tags,
                modules
        );
    }

    private CourseStructureDto.ModuleDto toModuleDto(CourseModule m) {
        List<CourseStructureDto.LessonDto> lessons = (m.getLessons() == null)
                ? List.of()
                : m.getLessons().stream()
                .map(this::toLessonDto)
                .toList();

        return new CourseStructureDto.ModuleDto(
                m.getId(),
                m.getTitle(),
                m.getOrderIndex(),
                lessons
        );
    }

    private CourseStructureDto.LessonDto toLessonDto(Lesson l) {
        return new CourseStructureDto.LessonDto(
                l.getId(),
                l.getTitle(),
                null
        );
    }
}
