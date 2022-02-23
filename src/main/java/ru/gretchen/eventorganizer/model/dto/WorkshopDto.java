package ru.gretchen.eventorganizer.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(name = "Workshop", description = "Workshop")
public class WorkshopDto {

    @Schema(description = "Workshop id",
            required = true,
            pattern = "*.",
            maxLength = 36,
            minLength = 36)
    UUID id;

    @Schema(description = "Workshop date time")
    LocalDate dateTime;

    @Schema(description = "Workshop topic",
            required = true)
    String topic;

    @Schema(description = "Workshop description",
            required = true)
    String description;

    @Schema(description = "Speaker user id")
    UUID speakerId;

    @Schema(description = "EventId")
    UUID eventId;
}
