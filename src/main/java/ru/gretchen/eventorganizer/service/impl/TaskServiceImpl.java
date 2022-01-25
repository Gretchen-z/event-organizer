package ru.gretchen.eventorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gretchen.eventorganizer.model.entity.Task;
import ru.gretchen.eventorganizer.model.enumeration.TaskStatus;
import ru.gretchen.eventorganizer.model.mapper.TaskMapper;
import ru.gretchen.eventorganizer.repository.TaskRepository;
import ru.gretchen.eventorganizer.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    public final TaskRepository taskRepository;
    public final TaskMapper taskMapper;

    @Override
    public Task get(Long id) {
        return taskRepository.getById(id);
    }

    @Override
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getAllByExecutor(UUID executorId) {
        return taskRepository.findAllByExecutor_UserId(executorId);
    }

    @Override
    public List<Task> getAllByDeadline(LocalDateTime deadline) {
        return taskRepository.findAllByDeadlineGreaterThan(deadline);
    }

    @Override
    public List<Task> getAllByStatus(TaskStatus status) {
        return taskRepository.findAllByStatus(status);
    }

    //todo: поиск по части описания
    @Override
    public List<Task> getAllByDescription(String description) {
        return taskRepository.findAllByDescription(description);
    }

    @Override
    public Task create(Task taskJson) {
        return taskRepository.save(taskJson);
    }

    @Override
    public Task update(Long id, Task taskJson) {
        return Optional.of(id)
                .map(this::get)
                .map(current -> taskMapper.merge(current, taskJson))
                .map(taskRepository::save)
                .orElseThrow();
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
