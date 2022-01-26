package ru.gretchen.eventorganizer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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
import java.util.ArrayList;
import java.util.List;
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
    public List<TaskDto> getAll() {
        List<Task> tasks = taskService.getAll();
        List<TaskDto> taskDto = new ArrayList<>();
        for (Task task:tasks) {
            taskDto.add(taskMapper.toDto(task));
        }
        return taskDto;
    }

    @GetMapping("/{executorId}")
    public List<TaskDto> getAllByExecutor(@PathVariable(name = "executorId") UUID executorId) {
        List<Task> tasks = taskService.getAllByExecutor(executorId);
        List<TaskDto> taskDto = new ArrayList<>();
        for (Task task:tasks) {
            taskDto.add(taskMapper.toDto(task));
        }
        return taskDto;
    }

    @GetMapping("/{deadline}")
    public List<TaskDto> getAllByDeadline(@PathVariable(name = "deadline") LocalDateTime deadline) {
        List<Task> tasks = taskService.getAllByDeadline(deadline);
        List<TaskDto> taskDto = new ArrayList<>();
        for (Task task:tasks) {
            taskDto.add(taskMapper.toDto(task));
        }
        return taskDto;
    }

    @GetMapping("/{status}")
    public List<TaskDto> getAllByStatus(@PathVariable(name = "status") TaskStatus status) {
        List<Task> tasks = taskService.getAllByStatus(status);
        List<TaskDto> taskDto = new ArrayList<>();
        for (Task task:tasks) {
            taskDto.add(taskMapper.toDto(task));
        }
        return taskDto;
    }

    @GetMapping("/{description}")
    public List<TaskDto> getAllByDescription(@PathVariable(name = "description") String description) {
        List<Task> tasks = taskService.getAllByDescription(description);
        List<TaskDto> taskDto = new ArrayList<>();
        for (Task task:tasks) {
            taskDto.add(taskMapper.toDto(task));
        }
        return taskDto;
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
