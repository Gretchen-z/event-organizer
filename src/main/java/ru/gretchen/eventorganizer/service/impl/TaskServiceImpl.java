package ru.gretchen.eventorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.gretchen.eventorganizer.model.entity.Task;
import ru.gretchen.eventorganizer.model.enumeration.TaskStatus;
import ru.gretchen.eventorganizer.model.mapper.TaskMapper;
import ru.gretchen.eventorganizer.repository.TaskRepository;
import ru.gretchen.eventorganizer.service.TaskService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    public final TaskRepository taskRepository;
    public final TaskMapper taskMapper;

    @Override
    public Task get(UUID id) {
        return taskRepository.findById(id).orElseThrow();
    }

    @Override
    public Page<Task> getAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Override
    public Page<Task> getAllByExecutor(UUID executorId, Pageable pageable) {
        return taskRepository.findAllByExecutorId(executorId, pageable);
    }

    @Override
    public Page<Task> getAllByDeadline(LocalDateTime deadline, Pageable pageable) {
        return taskRepository.findAllByDeadlineGreaterThan(deadline, pageable);
    }

    @Override
    public Page<Task> getAllByStatus(TaskStatus status, Pageable pageable) {
        return taskRepository.findAllByStatus(status, pageable);
    }

    //todo: поиск по части описания
    @Override
    public Page<Task> getAllByDescription(String description, Pageable pageable) {
        return taskRepository.findAllByDescription(description, pageable);
    }

    @Override
    public Task create(Task taskJson) {
        return taskRepository.save(taskJson);
    }

    @Override
    public Task update(UUID id, Task taskJson) {
        return Optional.of(id)
                .map(this::get)
                .map(current -> taskMapper.merge(current, taskJson))
                .map(taskRepository::save)
                .orElseThrow();
    }

    @Override
    public void delete(UUID id) {
        final Task task = taskRepository.findById(id).orElseThrow();
        taskRepository.delete(task);
    }
}
