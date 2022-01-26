package ru.gretchen.eventorganizer.model.dto;

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
import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskCreateDto {

    @PastOrPresent
    LocalDateTime dateCreating;

    @FutureOrPresent
    LocalDateTime deadline;

    UUID executorId;

    @NotBlank(message = "Поле \"Описание задачи\" не может быть пустым")
    @Size(max = 1000, message = "Поле \"Описание задачи\" не может содержать более 1000 символов")
    String description;

    TaskStatus status;
}
