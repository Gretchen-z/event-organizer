package ru.gretchen.eventorganizer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.gretchen.eventorganizer.model.dto.EventCreateDto;
import ru.gretchen.eventorganizer.model.dto.EventDto;
import ru.gretchen.eventorganizer.model.dto.EventUpdateDto;
import ru.gretchen.eventorganizer.model.dto.PaginationDto;
import ru.gretchen.eventorganizer.model.entity.Event;
import ru.gretchen.eventorganizer.model.exception.EventNotFoundException;
import ru.gretchen.eventorganizer.model.mapper.EventMapper;
import ru.gretchen.eventorganizer.service.EventService;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
@Tag(name = "Event", description = "Event management")
@ApiResponse(responseCode = "500", description = "Internal error")
@ApiResponse(responseCode = "400", description = "Validation failed")
@ApiResponse(responseCode = "404", description = "Event not found")
public class EventController {
    private final EventMapper eventMapper;
    private final EventService eventService;

    @Operation(description = "Find event by id")
    @ApiResponse(responseCode = "200", description = "Event found")
    @GetMapping("/{id}")
    public EventDto get(@PathVariable(name = "id") UUID id) {
        return Optional.of(id)
                .map(eventService::get)
                .map(eventMapper::toDto)
                .orElseThrow(() -> new EventNotFoundException(id));
    }

    @Operation(description = "Find all events")
    @ApiResponse(responseCode = "200", description = "Events found")
    @GetMapping
    public Page<EventDto> getAll(@RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<Event> events = eventService.getAll(pageable);
        return events.map(eventMapper::toDto);
    }

    @Operation(description = "Find all events by userId")
    @ApiResponse(responseCode = "200", description = "Events found")
    @GetMapping("/{userId}")
    public Page<EventDto> getAllByUserId(@PathVariable(name = "userId") UUID userId, @RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<Event> events = eventService.getAllByUserId(userId, pageable);
        return events.map(eventMapper::toDto);
    }

    @Operation(description = "Find all events by dateTime")
    @ApiResponse(responseCode = "200", description = "Events found")
    @GetMapping("/{dateTime}")
    public Page<EventDto> filterByDateTime(@PathVariable(name = "dateTime") ZonedDateTime dateTime, @RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        dateTime = ZonedDateTime.now();
        Page<Event> events = eventService.filterByDateTime(dateTime, pageable);
        return events.map(eventMapper::toDto);
    }

    @Operation(description = "Create event")
    @ApiResponse(responseCode = "200", description = "Event created")
    @PostMapping
    public EventDto create(@RequestBody @Valid EventCreateDto createDto) {
        return Optional.ofNullable(createDto)
                .map(eventMapper::fromCreateDto)
                .map(eventService::create)
                .map(eventMapper::toDto)
                .orElseThrow();
    }

    @Operation(description = "Update event by id")
    @ApiResponse(responseCode = "200", description = "Event updated")
    @PatchMapping("/{id}")
    public EventDto update(@PathVariable(name = "id") UUID id, @RequestBody @Valid EventUpdateDto updateDto) {
        return Optional.ofNullable(updateDto)
                .map(eventMapper::fromUpdateDto)
                .map(toUpdate -> eventService.update(id, toUpdate))
                .map(eventMapper::toDto)
                .orElseThrow();
    }

    @Operation(description = "Remove event by id")
    @ApiResponse(responseCode = "204", description = "Event removed")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") UUID id) {
        eventService.delete(id);
    }
}
