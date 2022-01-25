package ru.gretchen.eventorganizer.service;

import ru.gretchen.eventorganizer.model.entity.Task;
import ru.gretchen.eventorganizer.model.entity.Workshop;
import ru.gretchen.eventorganizer.model.enumeration.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TaskService {
    Task get(Long id);

    List<Task> getAll();

    List<Task> getAllByExecutor(UUID executorId);

    List<Task> getAllByDeadline(LocalDateTime deadline);

    List<Task> getAllByStatus(TaskStatus status);

    List<Task> getAllByDescription(String description);

    Task create(Task taskJson);

    Task update(Long id, Task taskJson);

    void delete(Long id);
}
