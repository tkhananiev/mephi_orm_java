package com.example.ormplatform.dto;

import com.example.ormplatform.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @Email @NotBlank String email,
        UserRole role
) {}
