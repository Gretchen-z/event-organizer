package ru.gretchen.eventorganizer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.gretchen.eventorganizer.model.dto.PaginationDto;
import ru.gretchen.eventorganizer.model.dto.UserCreateDto;
import ru.gretchen.eventorganizer.model.dto.UserDto;
import ru.gretchen.eventorganizer.model.dto.UserUpdateDto;
import ru.gretchen.eventorganizer.model.entity.User;
import ru.gretchen.eventorganizer.model.exception.UserNotFoundException;
import ru.gretchen.eventorganizer.model.mapper.UserMapper;
import ru.gretchen.eventorganizer.service.UserService;

import javax.validation.Valid;
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
    public Page<UserDto> getAll(@RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<User> users = userService.getAll(pageable);
        return users.map(userMapper::toDto);
    }

    @GetMapping("/{eventId}")
    public Page<UserDto> getAllByEvent(@PathVariable(name = "eventId") Long eventId, @RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<User> users = userService.getAllByEventId(eventId, pageable);
        return users.map(userMapper::toDto);
    }

    @GetMapping("/{surname}")
    public Page<UserDto> getAllBySurname(@PathVariable(name = "surname")String surname, @RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<User> users = userService.getAllBySurname(surname, pageable);
        return users.map(userMapper::toDto);
    }

    @PostMapping
    public UserDto create(@RequestBody @Valid UserCreateDto createDto) {
        return Optional.ofNullable(createDto)
                .map(userMapper::fromCreateDto)
                .map(userService::create)
                .map(userMapper::toDto)
                .orElseThrow();
    }

    @PatchMapping("/{userId}")
    public UserDto update(@PathVariable(name = "userId") UUID userId, @RequestBody @Valid UserUpdateDto updateDto) {
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
