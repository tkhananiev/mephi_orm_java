package com.example.ormplatform.api;

import com.example.ormplatform.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CoursesController {

    private final CourseService courseService;

    @GetMapping
    public List<CourseService.CourseSummaryDto> list() {
        return courseService.listCourses();
    }

    @GetMapping("/{id}")
    public CourseService.CourseSummaryDto get(@PathVariable Long id) {
        return courseService.getCourse(id);
    }

    @GetMapping("/{id}/structure")
    public CourseService.CourseStructureDto getWithStructure(@PathVariable Long id) {
        return courseService.getCourseWithStructure(id);
    }
}
