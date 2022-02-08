package ru.gretchen.eventorganizer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.gretchen.eventorganizer.model.dto.PaginationDto;
import ru.gretchen.eventorganizer.model.dto.WorkshopCreateDto;
import ru.gretchen.eventorganizer.model.dto.WorkshopDto;
import ru.gretchen.eventorganizer.model.dto.WorkshopUpdateDto;
import ru.gretchen.eventorganizer.model.entity.Workshop;
import ru.gretchen.eventorganizer.model.exception.WorkshopNotFoundException;
import ru.gretchen.eventorganizer.model.mapper.WorkshopMapper;
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
    private final WorkshopMapper workshopMapper;
    private final WorkshopService workshopService;

    @Operation(description = "Workshop event by id")
    @ApiResponse(responseCode = "200", description = "Workshop found")
    @GetMapping("/{id}")
    public WorkshopDto get(@PathVariable(name = "id") UUID id) {
        return Optional.of(id)
                .map(workshopService::get)
                .map(workshopMapper::toDto)
                .orElseThrow(() -> new WorkshopNotFoundException(id));
    }

    @Operation(description = "Find all workshops")
    @ApiResponse(responseCode = "200", description = "Workshops found")
    @GetMapping
    public Page<WorkshopDto> getAll(@RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<Workshop> workshops = workshopService.getAll(pageable);
        return workshops.map(workshopMapper::toDto);
    }

    @Operation(description = "Find all workshops by eventId")
    @ApiResponse(responseCode = "200", description = "Workshops found")
    @GetMapping("/{eventId}")
    public Page<WorkshopDto> getAllByEvent(@PathVariable(name = "eventId") UUID eventId, @RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<Workshop> workshops = workshopService.getAllByEvent(eventId, pageable);
        return workshops.map(workshopMapper::toDto);
    }

    @Operation(description = "Find all workshops by speakerId")
    @ApiResponse(responseCode = "200", description = "Workshops found")
    @GetMapping("/{speakerId}")
    public Page<WorkshopDto> getAllBySpeaker(@PathVariable(name = "speakerId") UUID speakerId, @RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<Workshop> workshops = workshopService.getAllBySpeaker(speakerId, pageable);
        return workshops.map(workshopMapper::toDto);
    }

    @Operation(description = "Find all workshops by dateTime")
    @ApiResponse(responseCode = "200", description = "Workshops found")
    @GetMapping("/dateTimeNow")
    public Page<WorkshopDto> getAllByDateTimeNow(@RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<Workshop> workshops = workshopService.getAllByDateTime(ZonedDateTime.now(), pageable);
        return workshops.map(workshopMapper::toDto);
    }

    @Operation(description = "Create workshop")
    @ApiResponse(responseCode = "200", description = "Workshop created")
    @PostMapping
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
    public void delete(@PathVariable(name = "id") UUID id) {
        workshopService.delete(id);
    }
}
