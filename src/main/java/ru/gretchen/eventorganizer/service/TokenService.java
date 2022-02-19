package ru.gretchen.eventorganizer.service;

import ru.gretchen.eventorganizer.model.entity.User;

public interface TokenService {
    String generateToken(User user);

    String extractUsernameAndValidate(String token);
}
