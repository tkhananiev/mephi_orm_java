package com.example.ormplatform.service;

import com.example.ormplatform.entity.Course;
import com.example.ormplatform.entity.CourseModule;
import com.example.ormplatform.entity.Lesson;
import com.example.ormplatform.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public record CourseSummaryDto(
            Long id,
            String title,
            String description,
            Integer durationHours,
            LocalDate startDate,
            Long categoryId,
            Long teacherId
    ) { }

    public record LessonDto(Long id, String title, String content, String videoUrl) { }

    public record ModuleDto(
            Long id,
            String title,
            Integer orderIndex,
            String description,
            List<LessonDto> lessons
    ) { }

    public record CourseStructureDto(
            Long id,
            String title,
            String description,
            Integer durationHours,
            LocalDate startDate,
            Long categoryId,
            Long teacherId,
            List<ModuleDto> modules
    ) { }

    @Transactional(readOnly = true)
    public List<CourseSummaryDto> listCourses() {
        return courseRepository.findAllProjectedBy().stream()
                .map(v -> new CourseSummaryDto(
                        v.getId(),
                        v.getTitle(),
                        v.getDescription(),
                        v.getDurationHours(),
                        v.getStartDate(),
                        v.getCategory() != null ? v.getCategory().getId() : null,
                        v.getTeacher() != null ? v.getTeacher().getId() : null
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public CourseSummaryDto getCourse(Long id) {
        var v = courseRepository.findProjectedById(id)
                .orElseThrow(() -> new NotFoundException("Course not found: " + id));

        return new CourseSummaryDto(
                v.getId(),
                v.getTitle(),
                v.getDescription(),
                v.getDurationHours(),
                v.getStartDate(),
                v.getCategory() != null ? v.getCategory().getId() : null,
                v.getTeacher() != null ? v.getTeacher().getId() : null
        );
    }

    @Transactional(readOnly = true)
    public CourseStructureDto getCourseWithStructure(Long id) {
        Course course = courseRepository.findWithStructureById(id)
                .orElseThrow(() -> new NotFoundException("Course not found: " + id));

        List<ModuleDto> modules = course.getModules().stream()
                .map(m -> new ModuleDto(
                        m.getId(),
                        m.getTitle(),
                        m.getOrderIndex(),
                        m.getDescription(),
                        m.getLessons().stream()
                                .map(l -> new LessonDto(
                                        l.getId(),
                                        l.getTitle(),
                                        l.getContent(),
                                        l.getVideoUrl()
                                ))
                                .toList()
                ))
                .toList();

        return new CourseStructureDto(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getDurationHours(),
                course.getStartDate(),
                course.getCategory() != null ? course.getCategory().getId() : null,
                course.getTeacher() != null ? course.getTeacher().getId() : null,
                modules
        );
    }
}
