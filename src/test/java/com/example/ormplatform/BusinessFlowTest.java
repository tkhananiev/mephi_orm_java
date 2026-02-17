package com.example.ormplatform;

import com.example.ormplatform.dto.CreateQuizRequest;
import com.example.ormplatform.entity.Category;
import com.example.ormplatform.entity.Course;
import com.example.ormplatform.entity.CourseModule;
import com.example.ormplatform.entity.User;
import com.example.ormplatform.entity.UserRole;
import com.example.ormplatform.repository.CategoryRepository;
import com.example.ormplatform.repository.CourseModuleRepository;
import com.example.ormplatform.repository.UserRepository;
import com.example.ormplatform.service.AssignmentService;
import com.example.ormplatform.service.CourseService;
import com.example.ormplatform.service.EnrollmentService;
import com.example.ormplatform.service.QuizService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BusinessFlowTest extends IntegrationTestBase {

    @Autowired UserRepository userRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired CourseModuleRepository courseModuleRepository;

    @Autowired CourseService courseService;
    @Autowired EnrollmentService enrollmentService;
    @Autowired AssignmentService assignmentService;
    @Autowired QuizService quizService;

    @Test
    void fullFlowSmoke() {
        User teacher = userRepository.save(User.builder().email("teach@test.com").role(UserRole.TEACHER).build());
        User student = userRepository.save(User.builder().email("stud@test.com").role(UserRole.STUDENT).build());
        Category cat = categoryRepository.save(Category.builder().name("Java").build());

        Course course = courseService.createCourse("Course1", "desc", cat.getId(), teacher.getId(), 10, null, Set.of("tag1"));
        CourseModule module = courseService.addModule(course.getId(), "Module1", 1, "m");
        assertNotNull(module.getId());

        var enr = enrollmentService.enroll(course.getId(), student.getId());
        assertNotNull(enr.getId());

        var asg = assignmentService.createAssignment(module.getId(), "A1", "d", 10);
        var sub = assignmentService.submit(asg.getId(), student.getId(), "solution");
        assertNotNull(sub.getId());
        var graded = assignmentService.grade(sub.getId(), 8);
        assertEquals(8, graded.getScore());

        CreateQuizRequest quizReq = new CreateQuizRequest(
                module.getId(),
                "Quiz1",
                List.of(new CreateQuizRequest.QuestionCreate(
                        "Q1",
                        List.of(new CreateQuizRequest.OptionCreate("O1", true),
                                new CreateQuizRequest.OptionCreate("O2", false))
                ))
        );
        var quiz = quizService.createQuiz(quizReq);
        assertNotNull(quiz.getId());

        Long correctOptionId = quiz.getQuestions().iterator().next().getOptions().stream().filter(o -> o.isCorrect()).findFirst().orElseThrow().getId();
        var qs = quizService.takeQuiz(quiz.getId(), student.getId(), Set.of(correctOptionId));
        assertEquals(1, qs.getScore());
    }
}
