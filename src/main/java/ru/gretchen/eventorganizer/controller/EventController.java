package ru.gretchen.eventorganizer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.gretchen.eventorganizer.model.dto.*;
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
    private static final int DEFAULT_PAGINATION_DATA_LIMIT = 10;
    private static final int DEFAULT_PAGE_NUM = 1;

    private final EventMapper eventMapper;
    private final EventService eventService;

    @Operation(description = "Find event by id")
    @ApiResponse(responseCode = "200", description = "Event found")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public EventDto get(@PathVariable(name = "id") UUID id) {
        return Optional.of(id)
                .map(eventService::getAndInitialize)
                .map(eventMapper::toDto)
                .orElseThrow(() -> new EventNotFoundException(id));
    }

    @Operation(description = "Find all events")
    @ApiResponse(responseCode = "200", description = "Events found")
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public Page<EventDto> getAll(@RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer page) {
        int datLimit = (limit == null) ? DEFAULT_PAGINATION_DATA_LIMIT : limit;
        int pageNum = (page == null) ? DEFAULT_PAGE_NUM : page;

        Pageable pageable = PageRequest.of(pageNum, datLimit);
        Page<Event> events = eventService.getAll(pageable);
        return events.map(eventMapper::toDto);
    }

    @Operation(description = "Find all events by userId")
    @ApiResponse(responseCode = "200", description = "Events found")
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER') || hasPermission(#userId, 'EVENT', 'READ')")
    public Page<EventDto> getAllByUserId(@PathVariable(name = "userId") UUID userId, @RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer page) {
        int datLimit = (limit == null) ? DEFAULT_PAGINATION_DATA_LIMIT : limit;
        int pageNum = (page == null) ? DEFAULT_PAGE_NUM : page;

        Pageable pageable = PageRequest.of(pageNum, datLimit);
        Page<Event> events = eventService.getAllByUserId(userId, pageable);
        return events.map(eventMapper::toDto);
    }

    @Operation(description = "Find all events by dateTime")
    @ApiResponse(responseCode = "200", description = "Events found")
    @GetMapping("/date-time-now")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public Page<EventDto> getAllByDateTimeNow(@RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer page) {
        int datLimit = (limit == null) ? DEFAULT_PAGINATION_DATA_LIMIT : limit;
        int pageNum = (page == null) ? DEFAULT_PAGE_NUM : page;

        Pageable pageable = PageRequest.of(pageNum, datLimit);
        Page<Event> events = eventService.getAllByDateTime(ZonedDateTime.now(), pageable);
        return events.map(eventMapper::toDto);
    }

    @Operation(description = "Create event")
    @ApiResponse(responseCode = "200", description = "Event created")
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
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
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
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
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public void delete(@PathVariable(name = "id") UUID id) {
        eventService.delete(id);
    }

    @Operation(description = "Add workshop by id")
    @ApiResponse(responseCode = "200", description = "Workshop added")
    @PatchMapping("/{id}/workshops/{workshopId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public void assignWorkshop(@PathVariable UUID id, @PathVariable UUID workshopId) {
        eventService.assignWorkshop(id, workshopId);
    }

    @Operation(description = "Remove workshop by id")
    @ApiResponse(responseCode = "204", description = "Workshop removed")
    @DeleteMapping("/{id}/workshops/{workshopId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public void deleteWorkshop(@PathVariable UUID id, @PathVariable UUID workshopId) {
        eventService.deleteWorkshop(id, workshopId);
    }

    @Operation(description = "Add user by id")
    @ApiResponse(responseCode = "200", description = "User added")
    @PatchMapping("/{id}/users/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public void assignUser(@PathVariable UUID id, @PathVariable UUID userId) {
        eventService.assignUser(id, userId);
    }

    @Operation(description = "Remove user by id")
    @ApiResponse(responseCode = "204", description = "User removed")
    @DeleteMapping("/{id}/users/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public void deleteUser(@PathVariable UUID id, @PathVariable UUID userId) {
        eventService.deleteUser(id, userId);
    }
}
