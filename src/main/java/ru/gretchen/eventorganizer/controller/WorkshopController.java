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
import ru.gretchen.eventorganizer.model.entity.Workshop;
import ru.gretchen.eventorganizer.model.exception.WorkshopNotFoundException;
import ru.gretchen.eventorganizer.model.mapper.WorkshopMapper;
import ru.gretchen.eventorganizer.service.UserService;
import ru.gretchen.eventorganizer.service.WorkshopService;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/workshops")
@Tag(name = "Workshop", description = "Workshop management")
@ApiResponse(responseCode = "500", description = "Internal error")
@ApiResponse(responseCode = "400", description = "Validation failed")
@ApiResponse(responseCode = "404", description = "Workshop not found")
public class WorkshopController {
    private static final int DEFAULT_PAGINATION_DATA_LIMIT = 10;
    private static final int DEFAULT_PAGE_NUM = 1;

    private final WorkshopMapper workshopMapper;
    private final WorkshopService workshopService;

    @Operation(description = "Workshop event by id")
    @ApiResponse(responseCode = "200", description = "Workshop found")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER') || hasPermission(#id, 'WORKSHOP_GET', 'READ')")
    public WorkshopDto get(@PathVariable(name = "id") UUID id) {
        return Optional.of(id)
                .map(workshopService::getAndInitialize)
                .map(workshopMapper::toDto)
                .orElseThrow(() -> new WorkshopNotFoundException(id));
    }

    @Operation(description = "Find all workshops")
    @ApiResponse(responseCode = "200", description = "Workshops found")
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public Page<WorkshopDto> getAll(@RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer page) {
        int datLimit = (limit == null) ? DEFAULT_PAGINATION_DATA_LIMIT : limit;
        int pageNum = (page == null) ? DEFAULT_PAGE_NUM : page;

        Pageable pageable = PageRequest.of(pageNum, datLimit);
        Page<Workshop> workshops = workshopService.getAll(pageable);
        return workshops.map(workshopMapper::toDto);
    }

    @Operation(description = "Find all workshops by eventId")
    @ApiResponse(responseCode = "200", description = "Workshops found")
    @GetMapping("/event/{eventId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER') || hasPermission(#eventId, 'WORKSHOP_EVENT', 'READ')")
    public Page<WorkshopDto> getAllByEvent(@PathVariable(name = "eventId") UUID eventId, @RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer page) {
        int datLimit = (limit == null) ? DEFAULT_PAGINATION_DATA_LIMIT : limit;
        int pageNum = (page == null) ? DEFAULT_PAGE_NUM : page;

        Pageable pageable = PageRequest.of(pageNum, datLimit);
        Page<Workshop> workshops = workshopService.getAllByEvent(eventId, pageable);
        return workshops.map(workshopMapper::toDto);
    }

    @Operation(description = "Find all workshops by speakerId")
    @ApiResponse(responseCode = "200", description = "Workshops found")
    @GetMapping("/speaker/{speakerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public Page<WorkshopDto> getAllBySpeaker(@PathVariable(name = "speakerId") UUID speakerId, @RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer page) {
        int datLimit = (limit == null) ? DEFAULT_PAGINATION_DATA_LIMIT : limit;
        int pageNum = (page == null) ? DEFAULT_PAGE_NUM : page;

        Pageable pageable = PageRequest.of(pageNum, datLimit);
        Page<Workshop> workshops = workshopService.getAllBySpeaker(speakerId, pageable);
        return workshops.map(workshopMapper::toDto);
    }

    @Operation(description = "Find all workshops by dateTime")
    @ApiResponse(responseCode = "200", description = "Workshops found")
    @GetMapping("/date-time-now")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public Page<WorkshopDto> getAllByDateTimeNow(@RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer page) {
        int datLimit = (limit == null) ? DEFAULT_PAGINATION_DATA_LIMIT : limit;
        int pageNum = (page == null) ? DEFAULT_PAGE_NUM : page;

        Pageable pageable = PageRequest.of(pageNum, datLimit);
        Page<Workshop> workshops = workshopService.getAllByDateTime(ZonedDateTime.now(), pageable);
        return workshops.map(workshopMapper::toDto);
    }

    @Operation(description = "Create workshop")
    @ApiResponse(responseCode = "200", description = "Workshop created")
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public WorkshopDto create(@RequestBody @Valid WorkshopCreateDto createDto) {
        return Optional.ofNullable(createDto)
                .map(workshopMapper::fromCreateDto)
                .map(workshopService::create)
                .map(workshopMapper::toDto)
                .orElseThrow();
    }

    @Operation(description = "Update workshop by id")
    @ApiResponse(responseCode = "200", description = "Workshop updated")
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public WorkshopDto update(@PathVariable(name = "id") UUID id, @RequestBody @Valid WorkshopUpdateDto updateDto) {
        return Optional.ofNullable(updateDto)
                .map(workshopMapper::fromUpdateDto)
                .map(toUpdate -> workshopService.update(id, toUpdate))
                .map(workshopMapper::toDto)
                .orElseThrow();
    }

    @Operation(description = "Remove workshop by id")
    @ApiResponse(responseCode = "204", description = "Workshop removed")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public void delete(@PathVariable(name = "id") UUID id) {
        workshopService.delete(id);
    }

    @Operation(description = "Add speaker by id")
    @ApiResponse(responseCode = "200", description = "Speaker added")
    @PatchMapping("/{id}/users/{speakerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public void assignSpeaker(@PathVariable UUID id, @PathVariable UUID speakerId) {
        workshopService.assignSpeaker(id, speakerId);
    }

    @Operation(description = "Remove speaker by id")
    @ApiResponse(responseCode = "204", description = "Speaker removed")
    @DeleteMapping("/{id}/users")
    @PreAuthorize("hasAnyRole('ADMIN', 'ORGANIZER')")
    public void deleteSpeaker(@PathVariable UUID id) {
        workshopService.deleteSpeaker(id);
    }
}
