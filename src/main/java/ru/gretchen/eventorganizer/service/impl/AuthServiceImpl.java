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

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final TokenService tokenService;

    @Override
    public String login(LoginRequest loginRequest) {
        User user = userService.getByEmail(loginRequest.getUsername());
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return tokenService.generateToken(user);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public String signUp(@Valid SignUpRequest signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setFirstName(signUpRequest.getFirstName());
        user.setSurname(signUpRequest.getSurname());
        user.setDateOfBirth(signUpRequest.getDateOfBirth());
        user.setGender(signUpRequest.getGender());
        user.setLocality(signUpRequest.getLocality());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setRegistrationDate(signUpRequest.getRegistrationDate());
        user.setRole(signUpRequest.getRole());
        userService.create(user);
        return tokenService.generateToken(user);
    }
}
