package ru.gretchen.eventorganizer.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.gretchen.eventorganizer.model.dto.security.LoginRequest;
import ru.gretchen.eventorganizer.model.entity.User;
import ru.gretchen.eventorganizer.model.exception.EmailNotExistsException;
import ru.gretchen.eventorganizer.service.TokenService;
import ru.gretchen.eventorganizer.service.UserService;
import ru.gretchen.eventorganizer.service.impl.AuthServiceImpl;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.same;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {
    @InjectMocks
    private AuthServiceImpl authServiceImpl;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserService userService;
    @Mock
    private TokenService tokenService;

    @BeforeAll
    public static void beforeClass() {
    }

    @BeforeEach
    public void beforeEach() {
    }

    @Test
    public void testUserNotFoundByEmail() {
        String username = "username";
        LoginRequest loginRequest = LoginRequest.builder().username(username).build();
        Mockito.when(userService.getByEmail(username));
        try {
            authServiceImpl.login(loginRequest);
            fail("");
        } catch (EmailNotExistsException e) {
            Assertions.assertEquals("User with email" + username + "not found", e.getMessage());
        }
    }

    @Test
    public void testMatchPasswordTrue() {
        String username = "username";
        LoginRequest loginRequest = LoginRequest.builder()
                .username(username)
                .password("password1")
                .build();
        User user = new User();
        user.setPassword("password");
        String exceptedToken = "exceptedToken";
        Mockito.when(userService.getByEmail(username));
        Mockito.when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(true);
        Mockito.when(tokenService.generateToken(user)).thenReturn(exceptedToken);
        String result = authServiceImpl.login(loginRequest);
        Mockito.verify(tokenService, Mockito.times(1)).generateToken(same(user));
        Assertions.assertEquals(exceptedToken, result);
    }

    @Test
    public void testMatchPasswordFalse() {
        String username = "username";
        LoginRequest loginRequest = LoginRequest.builder()
                .username(username)
                .password("password1")
                .build();
        User user = new User();
        user.setPassword("password");
        Mockito.when(userService.getByEmail(username));
        Mockito.when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(false);
        Assertions.assertThrows(RuntimeException.class, () -> authServiceImpl.login(loginRequest));
        Mockito.verify(tokenService, Mockito.times(0)).generateToken(same(user));
    }
}
