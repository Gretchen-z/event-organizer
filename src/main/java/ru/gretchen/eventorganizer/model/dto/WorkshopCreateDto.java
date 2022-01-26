package ru.gretchen.eventorganizer.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.UUID;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkshopCreateDto {

    @FutureOrPresent
    ZonedDateTime dateTime;

    @NotBlank(message = "Поле \"Тема мастеркласса\" не может быть пустым")
    @Size(max = 50, message = "Поле \"Тема мастеркласса\" не может содержать более 50 символов")
    String topic;

    @NotBlank(message = "Поле \"Описание мастеркласса\" не может быть пустым")
    @Size(max = 500, message = "Поле \"Описание мастеркласса\" не может содержать более 500 символов")
    String description;

    UUID speakerId;

    Long eventId;
}
