package ru.gretchen.eventorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import ru.gretchen.eventorganizer.model.entity.User;
import ru.gretchen.eventorganizer.model.mapper.UserMapper;
import ru.gretchen.eventorganizer.repository.UserRepository;
import ru.gretchen.eventorganizer.service.UserService;

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
    public Page<User> getAll(@PageableDefault(sort = { "userId" }, direction = Sort.Direction.ASC) Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> getAllByEventId(Long eventId, @PageableDefault(sort = { "userId" }, direction = Sort.Direction.ASC) Pageable pageable) {
        return userRepository.findAllByEvents_Id(eventId, pageable);
    }

    @Override
    public Page<User> getAllBySurname(String surname, @PageableDefault(sort = { "userId" }, direction = Sort.Direction.ASC) Pageable pageable) {
        return userRepository.findAllBySurname(surname, pageable);
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
