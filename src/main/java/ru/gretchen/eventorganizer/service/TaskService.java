package ru.gretchen.eventorganizer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.gretchen.eventorganizer.model.entity.Task;
import ru.gretchen.eventorganizer.model.enumeration.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TaskService {
    Task get(Long id);

    Page<Task> getAll(Pageable pageable);

    Page<Task> getAllByExecutor(UUID executorId, Pageable pageable);

    Page<Task> getAllByDeadline(LocalDateTime deadline, Pageable pageable);

    Page<Task> getAllByStatus(TaskStatus status, Pageable pageable);

    Page<Task> getAllByDescription(String description, Pageable pageable);

    Task create(Task taskJson);

    Task update(Long id, Task taskJson);

    void delete(Long id);
}
