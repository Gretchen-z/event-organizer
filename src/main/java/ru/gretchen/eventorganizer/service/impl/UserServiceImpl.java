package ru.gretchen.eventorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gretchen.eventorganizer.model.entity.User;
import ru.gretchen.eventorganizer.model.exception.EmailNotExistsException;
import ru.gretchen.eventorganizer.model.mapper.UserMapper;
import ru.gretchen.eventorganizer.repository.UserRepository;
import ru.gretchen.eventorganizer.service.UserService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User getAndInitialize(UUID id) {
        User result = userRepository.findById(id).orElseThrow();
        Hibernate.initialize(result);
        Hibernate.initialize(result.getEvents());
        return result;
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EmailNotExistsException(email));
    }

    @Override
    public Page<User> getAll(@PageableDefault(sort = { "id" }, direction = Sort.Direction.ASC) Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> getAllByEventId(UUID eventId, @PageableDefault(sort = { "id" }, direction = Sort.Direction.ASC) Pageable pageable) {
        return userRepository.findAllByEventsId(eventId, pageable);
    }

    @Override
    public Page<User> getAllBySurname(String surname, @PageableDefault(sort = { "id" }, direction = Sort.Direction.ASC) Pageable pageable) {
        return userRepository.findAllBySurname(surname, pageable);
    }

    @Override
    @Transactional
    public User create(User userJson) {
        return userRepository.save(userJson);
    }

    @Override
    @Transactional
    public User update(UUID id, User userJson) {
        return Optional.of(id)
                .map(this::getAndInitialize)
                .map(current -> userMapper.merge(current, userJson))
                .map(userRepository::save)
                .orElseThrow();
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        final User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
    }
}
