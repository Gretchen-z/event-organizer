package ru.gretchen.eventorganizer.service;

import ru.gretchen.eventorganizer.model.dto.security.LoginRequest;
import ru.gretchen.eventorganizer.model.dto.security.SignUpRequest;

public interface AuthService {
    String login(LoginRequest loginRequest);

    String signUp(SignUpRequest signUpRequest);
}
