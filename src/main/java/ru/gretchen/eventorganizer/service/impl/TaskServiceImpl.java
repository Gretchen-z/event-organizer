package ru.gretchen.eventorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {
    public final TaskRepository taskRepository;
    public final TaskMapper taskMapper;

    @Override
    public Task getAndInitialize(UUID id) {
        Task result = taskRepository.findById(id).orElseThrow();
        Hibernate.initialize(result);
        return result;
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

    @Override
    public Page<Task> getAllByDescription(String description, Pageable pageable) {
        return taskRepository.findAllByDescriptionContaining(description, pageable);
    }

    @Override
    @Transactional
    public Task create(Task taskJson) {
        return taskRepository.save(taskJson);
    }

    @Override
    @Transactional
    public Task update(UUID id, Task taskJson) {
        return Optional.of(id)
                .map(this::getAndInitialize)
                .map(current -> taskMapper.merge(current, taskJson))
                .map(taskRepository::save)
                .orElseThrow();
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        final Task task = taskRepository.findById(id).orElseThrow();
        taskRepository.delete(task);
    }
}
