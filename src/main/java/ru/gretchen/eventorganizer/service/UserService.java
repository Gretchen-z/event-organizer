package ru.gretchen.eventorganizer.service;

import ru.gretchen.eventorganizer.model.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User get(UUID userId);

    List<User> getAll();

    List<User> getAllByEventId(Long eventId);

    List<User> getAllBySurname(String surname);

    User create(User userJson);

    User update(UUID id, User userJson);

    void delete(UUID id);
}
