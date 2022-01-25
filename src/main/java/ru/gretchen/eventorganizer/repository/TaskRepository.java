package ru.gretchen.eventorganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gretchen.eventorganizer.model.entity.Task;
import ru.gretchen.eventorganizer.model.enumeration.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByExecutor_UserId(UUID executorId);

    List<Task> findAllByDeadlineGreaterThan(LocalDateTime deadline);

    List<Task> findAllByStatus(TaskStatus status);

    List<Task> findAllByDescription(String description);
}