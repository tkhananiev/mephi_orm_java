package com.example.ormplatform.config;

import com.example.ormplatform.entity.Category;
import com.example.ormplatform.entity.Course;
import com.example.ormplatform.entity.Tag;
import com.example.ormplatform.entity.User;
import com.example.ormplatform.entity.UserRole;
import com.example.ormplatform.repository.CategoryRepository;
import com.example.ormplatform.repository.CourseRepository;
import com.example.ormplatform.repository.TagRepository;
import com.example.ormplatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Set;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DevDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final CourseRepository courseRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) return;

        User teacher = userRepository.save(User.builder().email("teacher@example.com").role(UserRole.TEACHER).createdAt(Instant.now()).build());
        userRepository.save(User.builder().email("student@example.com").role(UserRole.STUDENT).createdAt(Instant.now()).build());

        Category cat = categoryRepository.save(Category.builder().name("Java").build());

        Tag t1 = tagRepository.save(Tag.builder().name("hibernate").build());
        Tag t2 = tagRepository.save(Tag.builder().name("spring").build());

        Course course = Course.builder()
                .title("ORM & JPA Basics")
                .description("Demo course")
                .category(cat)
                .teacher(teacher)
                .durationHours(10)
                .tags(Set.of(t1, t2))
                .build();

        courseRepository.save(course);
    }
}
