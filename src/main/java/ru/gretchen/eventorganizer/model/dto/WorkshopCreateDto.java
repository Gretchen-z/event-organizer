package ru.gretchen.eventorganizer.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(name = "WorkshopCreate", description = "Create workshop")
public class WorkshopCreateDto {

    @FutureOrPresent
    @Schema(description = "Workshop date time")
    LocalDate dateTime;

    @NotBlank(message = "Поле \"Тема мастеркласса\" не может быть пустым")
    @Size(max = 50, message = "Поле \"Тема мастеркласса\" не может содержать более 50 символов")
    @Schema(description = "Workshop topic",
            required = true)
    String topic;

    @NotBlank(message = "Поле \"Описание мастеркласса\" не может быть пустым")
    @Size(max = 500, message = "Поле \"Описание мастеркласса\" не может содержать более 500 символов")
    @Schema(description = "Workshop description",
            required = true)
    String description;

    @Schema(description = "Speaker user id")
    UUID speakerId;

    @Schema(description = "EventId")
    UUID eventId;
}
