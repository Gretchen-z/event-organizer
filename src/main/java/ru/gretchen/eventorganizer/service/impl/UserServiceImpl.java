package ru.gretchen.eventorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gretchen.eventorganizer.model.entity.User;
import ru.gretchen.eventorganizer.model.mapper.UserMapper;
import ru.gretchen.eventorganizer.repository.UserRepository;
import ru.gretchen.eventorganizer.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User get(UUID userId) {
        return userRepository.getById(userId);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAllByEventId(Long eventId) {
        return userRepository.findAllByEvents_Id(eventId);
    }

    @Override
    public List<User> getAllBySurname(String surname) {
        return userRepository.findAllBySurname(surname);
    }

    @Override
    public User create(User userJson) {
        return userRepository.save(userJson);
    }

    @Override
    public User update(UUID userId, User userJson) {
        return Optional.of(userId)
                .map(this::get)
                .map(current -> userMapper.merge(current, userJson))
                .map(userRepository::save)
                .orElseThrow();
    }

    @Override
    public void delete(UUID userId) {
        userRepository.deleteById(userId);
    }
}
