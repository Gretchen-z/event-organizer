package ru.gretchen.eventorganizer.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.gretchen.eventorganizer.model.enumeration.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskUpdateDto {
    LocalDateTime dateCreating;
    LocalDateTime deadline;
    UUID executorId;
    String description;
    TaskStatus status;
}
