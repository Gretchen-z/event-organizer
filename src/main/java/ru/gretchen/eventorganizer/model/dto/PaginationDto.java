package ru.gretchen.eventorganizer.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Positive;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(name = "Pagination", description = "Create pagination")
public class PaginationDto {

    @Positive
    @Schema(description = "limit",
            required = true)
    int limit;

    @Positive
    @Schema(description = "page",
            required = true)
    int page;
}
