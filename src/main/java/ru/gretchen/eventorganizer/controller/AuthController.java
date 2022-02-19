package ru.gretchen.eventorganizer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gretchen.eventorganizer.model.dto.security.SignUpRequest;
import ru.gretchen.eventorganizer.model.dto.security.LoginRequest;
import ru.gretchen.eventorganizer.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    };

    @PostMapping("/sign-up")
    public String signUp(@RequestBody SignUpRequest signUpRequest){
        return authService.signUp(signUpRequest);
    }
}
