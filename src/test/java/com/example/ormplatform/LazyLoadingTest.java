package com.example.ormplatform;

import com.example.ormplatform.entity.Category;
import com.example.ormplatform.entity.Course;
import com.example.ormplatform.entity.CourseModule;
import com.example.ormplatform.entity.User;
import com.example.ormplatform.entity.UserRole;
import com.example.ormplatform.repository.CategoryRepository;
import com.example.ormplatform.repository.CourseModuleRepository;
import com.example.ormplatform.repository.CourseRepository;
import com.example.ormplatform.repository.UserRepository;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import static org.junit.jupiter.api.Assertions.*;

public class LazyLoadingTest extends IntegrationTestBase {

    @Autowired UserRepository userRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired CourseRepository courseRepository;
    @Autowired CourseModuleRepository courseModuleRepository;
    @Autowired TransactionTemplate tx;

    @Test
    void accessingLazyCollectionOutsideTxThrows() {
        User teacher = userRepository.save(User.builder().email("t1@test.com").role(UserRole.TEACHER).build());
        Category cat = categoryRepository.save(Category.builder().name("cat1").build());
        Course course = courseRepository.save(Course.builder().title("c1").category(cat).teacher(teacher).build());
        courseModuleRepository.save(CourseModule.builder().course(course).title("m1").orderIndex(1).build());

        Course detached = courseRepository.findById(course.getId()).orElseThrow();
        assertThrows(LazyInitializationException.class, () -> detached.getModules().size());
    }

    @Test
    void accessingLazyCollectionInsideTxWorks() {
        User teacher = userRepository.save(User.builder().email("t2@test.com").role(UserRole.TEACHER).build());
        Category cat = categoryRepository.save(Category.builder().name("cat2").build());
        Course course = courseRepository.save(Course.builder().title("c2").category(cat).teacher(teacher).build());
        courseModuleRepository.save(CourseModule.builder().course(course).title("m2").orderIndex(1).build());

        Integer size = tx.execute(status -> {
            Course managed = courseRepository.getReferenceById(course.getId());
            return managed.getModules().size();
        });
        assertNotNull(size);
        assertTrue(size >= 1);
    }
}
