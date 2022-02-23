package ru.gretchen.eventorganizer.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.gretchen.eventorganizer.model.enumeration.TaskStatus;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(name = "TaskCreate", description = "Create task")
public class TaskCreateDto {

    @PastOrPresent
    @Schema(description = "Task date creating")
    LocalDate dateCreating;

    @FutureOrPresent
    @Schema(description = "Task deadline")
    LocalDate deadline;

    @Schema(description = "Executor user id")
    UUID executorId;

    @NotBlank(message = "Поле \"Описание задачи\" не может быть пустым")
    @Size(max = 1000, message = "Поле \"Описание задачи\" не может содержать более 1000 символов")
    @Schema(description = "Task description",
            required = true)
    String description;

    @Schema(description = "Task status")
    TaskStatus status;
}
