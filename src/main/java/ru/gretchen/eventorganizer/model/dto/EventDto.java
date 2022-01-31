package ru.gretchen.eventorganizer.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.ZonedDateTime;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(name = "Event", description = "Event")
public class EventDto {

    @Schema(description = "Event id",
            required = true)
    Long id;

    @Schema(description = "Event name",
            required = true)
    String name;

    @Schema(description = "Event topic")
    String topic;

    @Schema(description = "Event description")
    String description;

    @Schema(description = "Event locality")
    String locality;

    @Schema(description = "Event date time")
    ZonedDateTime dateTime;
}
