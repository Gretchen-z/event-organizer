package ru.gretchen.eventorganizer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.gretchen.eventorganizer.model.entity.User;

import java.util.UUID;

public interface UserService {
    User getAndInitialize(UUID id);

    Page<User> getAll(Pageable pageable);

    Page<User> getAllByEventId(UUID eventId, Pageable pageable);

    Page<User> getAllBySurname(String surname, Pageable pageable);

    User create(User userJson);

    User update(UUID id, User userJson);

    void delete(UUID id);
}
