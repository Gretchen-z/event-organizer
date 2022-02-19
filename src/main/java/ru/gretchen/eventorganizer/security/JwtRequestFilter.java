package ru.gretchen.eventorganizer.security;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.gretchen.eventorganizer.model.dto.UserDto;
import ru.gretchen.eventorganizer.model.entity.User;
import ru.gretchen.eventorganizer.model.exception.EmailNotExistsException;
import ru.gretchen.eventorganizer.model.exception.UserNotFoundException;
import ru.gretchen.eventorganizer.model.mapper.UserMapper;
import ru.gretchen.eventorganizer.service.TokenService;
import ru.gretchen.eventorganizer.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtRequestFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String token = request.getHeader("AUTHORIZATION");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (token != null && securityContext.getAuthentication() == null) {
            String username = tokenService.extractUsernameAndValidate(token);
            User user = userService.getByEmail(username);
            UserDto userDto = userMapper.toDto(user);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDto,
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + userDto.getRole().name())));
            securityContext.setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
