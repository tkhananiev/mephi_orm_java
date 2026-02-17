package com.example.ormplatform.api;

import com.example.ormplatform.dto.CreateUserRequest;
import com.example.ormplatform.entity.User;
import com.example.ormplatform.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;

    @PostMapping
    public User create(@Valid @RequestBody CreateUserRequest req) {
        return userService.createUser(req.email(), req.role(), req.email());
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping
    public List<User> list() {
        return userService.listUsers();
    }
}
