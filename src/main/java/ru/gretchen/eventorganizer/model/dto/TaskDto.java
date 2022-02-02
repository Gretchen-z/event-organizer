package ru.gretchen.eventorganizer.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "Task", description = "Task")
public class TaskDto {

    @Schema(description = "Task id",
            required = true,
            pattern = "*.",
            maxLength = 36,
            minLength = 36)
    UUID id;

    @Schema(description = "Task date creating")
    LocalDateTime dateCreating;

    @Schema(description = "Task deadline")
    LocalDateTime deadline;

    @Schema(description = "Executor user id")
    UUID executorId;

    @Schema(description = "Task description",
            required = true)
    String description;

    @Schema(description = "Task status")
    TaskStatus status;
}
