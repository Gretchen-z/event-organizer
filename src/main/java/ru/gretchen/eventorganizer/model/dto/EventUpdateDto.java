package ru.gretchen.eventorganizer.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(name = "EventUpdate", description = "Update event")
public class EventUpdateDto {

    @Size(max = 50, message = "Поле \"Название мероприятия\" не может содержать более 50 символов")
    @Schema(description = "Event name",
            required = true)
    String name;

    @Size(max = 100, message = "Поле \"Тематика мероприятия\" не может содержать более 100 символов")
    @Schema(description = "Event topic")
    String topic;

    @Size(max = 1000, message = "Поле \"Описание мероприятия\" не может содержать более 1000 символов")
    @Schema(description = "Event description")
    String description;

    @Size(max = 50, message = "Поле \"Населённый пункт\" не может содержать более 50 символов")
    @Schema(description = "Event locality")
    String locality;

    @FutureOrPresent
    @Schema(description = "Event date time")
    LocalDate dateTime;
}
