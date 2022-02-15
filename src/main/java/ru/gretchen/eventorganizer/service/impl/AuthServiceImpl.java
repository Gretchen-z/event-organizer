package ru.gretchen.eventorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.gretchen.eventorganizer.model.dto.security.LoginRequest;
import ru.gretchen.eventorganizer.model.dto.security.SignUpRequest;
import ru.gretchen.eventorganizer.model.entity.User;
import ru.gretchen.eventorganizer.service.AuthService;
import ru.gretchen.eventorganizer.service.TokenService;
import ru.gretchen.eventorganizer.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private TokenService tokenService;

    @Override
    public String login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public String signUp(SignUpRequest signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getUsername());
        user.setPassword(signUpRequest.getPassword());
        userService.create(user);
        return tokenService.generateToken(user);
    }
}
