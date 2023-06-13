package com.rest_api.fs14backend.auth;

import com.rest_api.fs14backend.user.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String picture;
    private String email;
    private Role role;
}

