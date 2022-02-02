package ru.gretchen.eventorganizer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
@Tag(name = "User", description = "User management")
@ApiResponse(responseCode = "500", description = "Internal error")
@ApiResponse(responseCode = "400", description = "Validation failed")
@ApiResponse(responseCode = "404", description = "User not found")
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;

    @Operation(description = "Find user by id")
    @ApiResponse(responseCode = "200", description = "User found")
    @GetMapping("/{id}")
    public UserDto get(@PathVariable(name = "id") UUID id) {
        return Optional.of(id)
                .map(userService::get)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Operation(description = "Find all users")
    @ApiResponse(responseCode = "200", description = "Users found")
    @GetMapping
    public Page<UserDto> getAll(@RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<User> users = userService.getAll(pageable);
        return users.map(userMapper::toDto);
    }

    @Operation(description = "Find all users by eventId")
    @ApiResponse(responseCode = "200", description = "Users found")
    @GetMapping("/{eventId}")
    public Page<UserDto> getAllByEvent(@PathVariable(name = "eventId") UUID eventId, @RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<User> users = userService.getAllByEventId(eventId, pageable);
        return users.map(userMapper::toDto);
    }

    @Operation(description = "Find all users by surname")
    @ApiResponse(responseCode = "200", description = "Users found")
    @GetMapping("/{surname}")
    public Page<UserDto> getAllBySurname(@PathVariable(name = "surname")String surname, @RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<User> users = userService.getAllBySurname(surname, pageable);
        return users.map(userMapper::toDto);
    }

    @Operation(description = "Create user")
    @ApiResponse(responseCode = "200", description = "User created")
    @PostMapping
    public UserDto create(@RequestBody @Valid UserCreateDto createDto) {
        return Optional.ofNullable(createDto)
                .map(userMapper::fromCreateDto)
                .map(userService::create)
                .map(userMapper::toDto)
                .orElseThrow();
    }

    @Operation(description = "Update user by id")
    @ApiResponse(responseCode = "200", description = "User updated")
    @PatchMapping("/{id}")
    public UserDto update(@PathVariable(name = "id") UUID id, @RequestBody @Valid UserUpdateDto updateDto) {
        return Optional.ofNullable(updateDto)
                .map(userMapper::fromUpdateDto)
                .map(toUpdate -> userService.update(id, toUpdate))
                .map(userMapper::toDto)
                .orElseThrow();
    }

    @Operation(description = "Remove user by id")
    @ApiResponse(responseCode = "204", description = "User removed")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") UUID id) {
        userService.delete(id);
    }
}
