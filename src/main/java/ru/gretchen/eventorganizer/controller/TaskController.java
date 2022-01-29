package ru.gretchen.eventorganizer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.gretchen.eventorganizer.model.dto.PaginationDto;
import ru.gretchen.eventorganizer.model.dto.TaskCreateDto;
import ru.gretchen.eventorganizer.model.dto.TaskDto;
import ru.gretchen.eventorganizer.model.dto.TaskUpdateDto;
import ru.gretchen.eventorganizer.model.entity.Task;
import ru.gretchen.eventorganizer.model.enumeration.TaskStatus;
import ru.gretchen.eventorganizer.model.exception.TaskNotFoundException;
import ru.gretchen.eventorganizer.model.mapper.TaskMapper;
import ru.gretchen.eventorganizer.service.TaskService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

// todo: swagger
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/tasks")
public class TaskController {
    public final TaskMapper taskMapper;
    public final TaskService taskService;

    @GetMapping("/{id}")
    public TaskDto get(@PathVariable(name = "id") Long id) {
        return Optional.of(id)
                .map(taskService::get)
                .map(taskMapper::toDto)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @GetMapping
    public Page<TaskDto> getAll(@RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<Task> tasks = taskService.getAll(pageable);
        return tasks.map(taskMapper::toDto);
    }

    @GetMapping("/{executorId}")
    public Page<TaskDto> getAllByExecutor(@PathVariable(name = "executorId") UUID executorId, @RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<Task> tasks = taskService.getAllByExecutor(executorId, pageable);
        return tasks.map(taskMapper::toDto);
    }

    @GetMapping("/{deadline}")
    public Page<TaskDto> getAllByDeadline(@PathVariable(name = "deadline") LocalDateTime deadline, @RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<Task> tasks = taskService.getAllByDeadline(deadline, pageable);
        return tasks.map(taskMapper::toDto);
    }

    @GetMapping("/{status}")
    public Page<TaskDto> getAllByStatus(@PathVariable(name = "status") TaskStatus status, @RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<Task> tasks = taskService.getAllByStatus(status, pageable);
        return tasks.map(taskMapper::toDto);
    }

    @GetMapping("/{description}")
    public Page<TaskDto> getAllByDescription(@PathVariable(name = "description") String description, @RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<Task> tasks = taskService.getAllByDescription(description, pageable);
        return tasks.map(taskMapper::toDto);
    }

    @PostMapping
    public TaskDto create(@RequestBody @Valid TaskCreateDto createDto) {
        return Optional.ofNullable(createDto)
                .map(taskMapper::fromCreateDto)
                .map(taskService::create)
                .map(taskMapper::toDto)
                .orElseThrow();
    }

    @PatchMapping("/{id}")
    public TaskDto update(@PathVariable(name = "id") Long id, @RequestBody @Valid TaskUpdateDto updateDto) {
        return Optional.ofNullable(updateDto)
                .map(taskMapper::fromUpdateDto)
                .map(toUpdate -> taskService.update(id, toUpdate))
                .map(taskMapper::toDto)
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        taskService.delete(id);
    }
}
