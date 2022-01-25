package ru.gretchen.eventorganizer.model.dto;

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
public class WorkshopCreateDto {
    // todo: validator
    ZonedDateTime dateTime;
    String topic;
    String description;
    UUID speakerId;
    Long eventId;
}
