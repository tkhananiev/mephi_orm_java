package com.example.ormplatform.service;

import com.example.ormplatform.entity.*;
import com.example.ormplatform.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CourseModuleRepository courseModuleRepository;
    private final LessonRepository lessonRepository;
    private final TagRepository tagRepository;

    @Transactional
    public Course createCourse(String title,
                               String description,
                               Long categoryId,
                               Long teacherId,
                               Integer durationHours,
                               LocalDate startDate,
                               Set<String> tagNames) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found: " + categoryId));

        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException("Teacher not found: " + teacherId));

        if (teacher.getRole() != UserRole.TEACHER && teacher.getRole() != UserRole.ADMIN) {
            throw new ConflictException("User is not allowed to be a teacher: " + teacherId);
        }

        Course course = Course.builder()
                .title(title)
                .description(description)
                .category(category)
                .teacher(teacher)
                .durationHours(durationHours)
                .startDate(startDate)
                .build();

        if (tagNames != null && !tagNames.isEmpty()) {
            Set<Tag> tags = new HashSet<>();
            for (String tagName : tagNames) {
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> tagRepository.save(Tag.builder().name(tagName).build()));
                tags.add(tag);
            }
            course.setTags(tags);
        }

        return courseRepository.save(course);
    }

    @Transactional
    public CourseModule addModule(Long courseId, String title, int orderIndex, String description) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found: " + courseId));

        CourseModule module = CourseModule.builder()
                .course(course)
                .title(title)
                .orderIndex(orderIndex)
                .description(description)
                .build();

        try {
            return courseModuleRepository.save(module);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Module with orderIndex=" + orderIndex + " already exists for course=" + courseId);
        }
    }

    /**
     * Lessons API
     */
    @Transactional
    public Lesson addLesson(Long moduleId, String title, String content, String videoUrl) {
        CourseModule module = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new NotFoundException("Module not found: " + moduleId));

        Lesson lesson = Lesson.builder()
                .courseModule(module)
                .title(title)
                .content(content)
                .videoUrl(videoUrl)
                .build();

        return lessonRepository.save(lesson);
    }

    /**
     * Course DELETE
     */
    @Transactional
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Course not found: " + id));

        // Важно: разорвать owning ManyToMany, чтобы корректно подчистилась join-таблица (например course_tag)
        if (course.getTags() != null && !course.getTags().isEmpty()) {
            course.getTags().clear();
        }

        try {
            courseRepository.delete(course);
            courseRepository.flush(); // чтобы FK/DataIntegrityViolation прилетел сразу и обработался глобальным handler’ом
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Course " + id + " cannot be deleted because it has dependent data");
        }
    }

    @Transactional(readOnly = true)
    public Course getCourse(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Course not found: " + id));
    }

    @Transactional(readOnly = true)
    public Course getCourseWithStructure(Long id) {
        return courseRepository.findWithStructureById(id)
                .orElseThrow(() -> new NotFoundException("Course not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Course> listCourses() {
        return courseRepository.findAll();
    }
}
