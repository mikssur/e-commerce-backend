package com.rest_api.fs14backend.auth;

import com.rest_api.fs14backend.SecurityConfig.JwtUtils;
import com.rest_api.fs14backend.user.User;
import com.rest_api.fs14backend.user.UserRepository;
import com.rest_api.fs14backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    public AuthResponse createUser(String name,  String email, String picture, String role) {
        User user = userService.createUser(name, email, picture, role);

        String jwtToken = getJwtToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }

    public AuthResponse googleAuthenticate(User user)
    {
        String jwtToken = getJwtToken(user);
        return AuthResponse.builder().token(jwtToken).build();
    }

    private String getJwtToken(User user) {
        return jwtUtils.generateToken(createExtraClaims(user), user);
    }

    private Map<String, Object> createExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<String, Object>();
        extraClaims.put("userId", user.getId());
        extraClaims.put("name", user.getName());
        extraClaims.put("email", user.getEmail());
        extraClaims.put("role", user.getRole());

        return extraClaims;
    }
}
