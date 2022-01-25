package ru.gretchen.eventorganizer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gretchen.eventorganizer.model.dto.UserCreateDto;
import ru.gretchen.eventorganizer.model.dto.UserDto;
import ru.gretchen.eventorganizer.model.dto.UserUpdateDto;
import ru.gretchen.eventorganizer.model.entity.User;
import ru.gretchen.eventorganizer.model.exception.UserNotFoundException;
import ru.gretchen.eventorganizer.model.mapper.UserMapper;
import ru.gretchen.eventorganizer.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// todo: swagger
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;

    @GetMapping("/{userId}")
    public UserDto get(@PathVariable(name = "userId") UUID userId) {
        return Optional.of(userId)
                .map(userService::get)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @GetMapping
    public List<UserDto> getAll() {
        List<User> users = userService.getAll();
        List<UserDto> userDto = new ArrayList<>();
        for (User user:users) {
            userDto.add(userMapper.toDto(user));
        }
        return userDto;
    }

    @GetMapping("/{eventId}")
    public List<UserDto> getAllByEvent(@PathVariable(name = "eventId") Long eventId) {
        List<User> users = userService.getAllByEventId(eventId);
        List<UserDto> userDto = new ArrayList<>();
        for (User user:users) {
            userDto.add(userMapper.toDto(user));
        }
        return userDto;
    }

    @GetMapping("/{surname}")
    public List<UserDto> getAllBySurname(@PathVariable(name = "surname")String surname) {
        List<User> users = userService.getAllBySurname(surname);
        List<UserDto> userDto = new ArrayList<>();
        for (User user:users) {
            userDto.add(userMapper.toDto(user));
        }
        return userDto;
    }

    @PostMapping
    public UserDto create(@RequestBody UserCreateDto createDto) {
        return Optional.ofNullable(createDto)
                .map(userMapper::fromCreateDto)
                .map(userService::create)
                .map(userMapper::toDto)
                .orElseThrow();
    }

    @PatchMapping("/{userId}")
    public UserDto update(@PathVariable(name = "userId") UUID userId, @RequestBody UserUpdateDto updateDto) {
        return Optional.ofNullable(updateDto)
                .map(userMapper::fromUpdateDto)
                .map(toUpdate -> userService.update(userId, toUpdate))
                .map(userMapper::toDto)
                .orElseThrow();
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable(name = "userId") UUID userId) {
        userService.delete(userId);
    }
}
