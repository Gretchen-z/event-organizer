package ru.gretchen.eventorganizer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.gretchen.eventorganizer.model.dto.*;
import ru.gretchen.eventorganizer.model.entity.Task;
import ru.gretchen.eventorganizer.model.enumeration.TaskStatus;
import ru.gretchen.eventorganizer.model.exception.TaskNotFoundException;
import ru.gretchen.eventorganizer.model.mapper.TaskMapper;
import ru.gretchen.eventorganizer.service.TaskService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/tasks")
@Tag(name = "Task", description = "Task management")
@ApiResponse(responseCode = "500", description = "Internal error")
@ApiResponse(responseCode = "400", description = "Validation failed")
@ApiResponse(responseCode = "404", description = "Task not found")
public class TaskController {
    public final TaskMapper taskMapper;
    public final TaskService taskService;

    @Operation(description = "Find task by id")
    @ApiResponse(responseCode = "200", description = "Task found")
    @GetMapping("/{id}")
    public TaskDto get(@PathVariable(name = "id") UUID id) {
        return Optional.of(id)
                .map(taskService::getAndInitialize)
                .map(taskMapper::toDto)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Operation(description = "Find all tasks")
    @ApiResponse(responseCode = "200", description = "Tasks found")
    @GetMapping
    public Page<TaskDto> getAll(@RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<Task> tasks = taskService.getAll(pageable);
        return tasks.map(taskMapper::toDto);
    }

    @Operation(description = "Find all tasks by executorId")
    @ApiResponse(responseCode = "200", description = "Tasks found")
    @GetMapping("/{executorId}")
    public Page<TaskDto> getAllByExecutor(@PathVariable(name = "executorId") UUID executorId, @RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<Task> tasks = taskService.getAllByExecutor(executorId, pageable);
        return tasks.map(taskMapper::toDto);
    }

    @Operation(description = "Find all tasks by deadline")
    @ApiResponse(responseCode = "200", description = "Tasks found")
    @GetMapping("/{deadline}")
    public Page<TaskDto> getAllByDeadline(@PathVariable(name = "deadline") LocalDateTime deadline, @RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<Task> tasks = taskService.getAllByDeadline(deadline, pageable);
        return tasks.map(taskMapper::toDto);
    }

    @Operation(description = "Find all tasks by status")
    @ApiResponse(responseCode = "200", description = "Tasks found")
    @GetMapping("/{status}")
    public Page<TaskDto> getAllByStatus(@PathVariable(name = "status") TaskStatus status, @RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<Task> tasks = taskService.getAllByStatus(status, pageable);
        return tasks.map(taskMapper::toDto);
    }

    @Operation(description = "Find all tasks by description")
    @ApiResponse(responseCode = "200", description = "Tasks found")
    @GetMapping("/{description}")
    public Page<TaskDto> getAllByDescription(@PathVariable(name = "description") String description, @RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<Task> tasks = taskService.getAllByDescription(description, pageable);
        return tasks.map(taskMapper::toDto);
    }

    @Operation(description = "Create task")
    @ApiResponse(responseCode = "200", description = "Task created")
    @PostMapping
    public TaskDto create(@RequestBody @Valid TaskCreateDto createDto) {
        return Optional.ofNullable(createDto)
                .map(taskMapper::fromCreateDto)
                .map(taskService::create)
                .map(taskMapper::toDto)
                .orElseThrow();
    }

    @Operation(description = "Update task by id")
    @ApiResponse(responseCode = "200", description = "Task updated")
    @PatchMapping("/{id}")
    public TaskDto update(@PathVariable(name = "id") UUID id, @RequestBody @Valid TaskUpdateDto updateDto) {
        return Optional.ofNullable(updateDto)
                .map(taskMapper::fromUpdateDto)
                .map(toUpdate -> taskService.update(id, toUpdate))
                .map(taskMapper::toDto)
                .orElseThrow();
    }

    @Operation(description = "Remove task by id")
    @ApiResponse(responseCode = "204", description = "Task removed")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") UUID id) {
        taskService.delete(id);
    }

    @GetMapping("/{id}/executors/{executorId}")
    public UserDto getUser(@PathVariable UUID id, @PathVariable UUID executorId) {
        return null;
    }

    @PostMapping("/{id}/executors")
    public UserDto assignUser(@PathVariable UUID id, @RequestBody UserCreateDto createDto) {
        return null;
    }

    @PatchMapping("/{id}/executors/{executorId}")
    public UserDto updateUser(@PathVariable UUID id, @PathVariable UUID executorId, @RequestBody EventUpdateDto updateDto) {
        return null;
    }

    @DeleteMapping("/{id}/executors/{executorId}")
    public void deleteUser(@PathVariable UUID id, @PathVariable UUID executorId) {
    }
}
