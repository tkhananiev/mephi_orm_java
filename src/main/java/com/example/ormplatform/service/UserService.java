package com.example.ormplatform.service;

import com.example.ormplatform.entity.Profile;
import com.example.ormplatform.entity.User;
import com.example.ormplatform.entity.UserRole;
import com.example.ormplatform.repository.ProfileRepository;
import com.example.ormplatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @Transactional
    public User createUser(String email, UserRole role, String fullName) {
        userRepository.findByEmail(email).ifPresent(u -> {
            throw new ConflictException("Email already exists: " + email);
        });

        User user = User.builder()
                .email(email)
                .role(role == null ? UserRole.STUDENT : role)
                .build();

        User saved = userRepository.save(user);

        profileRepository.save(Profile.builder()
                .user(saved)
                .fullName(fullName == null ? email : fullName)
                .build());

        return saved;
    }

    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userRepository.findAll();
    }
}
