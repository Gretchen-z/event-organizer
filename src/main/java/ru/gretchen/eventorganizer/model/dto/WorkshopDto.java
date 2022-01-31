package ru.gretchen.eventorganizer.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.ZonedDateTime;
import java.util.UUID;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(name = "Workshop", description = "Workshop")
public class WorkshopDto {

    @Schema(description = "Workshop id",
            required = true)
    Long id;

    @Schema(description = "Workshop date time")
    ZonedDateTime dateTime;

    @Schema(description = "Workshop topic",
            required = true)
    String topic;

    @Schema(description = "Workshop description",
            required = true)
    String description;

    @Schema(description = "Speaker userId")
    UUID speakerId;

    @Schema(description = "EventId")
    Long eventId;
}
