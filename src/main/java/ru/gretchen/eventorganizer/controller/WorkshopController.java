package ru.gretchen.eventorganizer.controller;

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

// todo: swagger
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/workshops")
public class WorkshopController {
    private final WorkshopMapper workshopMapper;
    private final WorkshopService workshopService;

    @GetMapping("/{id}")
    public WorkshopDto get(@PathVariable(name = "id") Long id) {
        return Optional.of(id)
                .map(workshopService::get)
                .map(workshopMapper::toDto)
                .orElseThrow(() -> new WorkshopNotFoundException(id));
    }

    @GetMapping
    public Page<WorkshopDto> getAll(@RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<Workshop> workshops = workshopService.getAll(pageable);
        return workshops.map(workshopMapper::toDto);
    }

    @GetMapping("/{eventId}")
    public Page<WorkshopDto> getAllByEvent(@PathVariable(name = "eventId") Long eventId, @RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<Workshop> workshops = workshopService.getAllByEvent(eventId, pageable);
        return workshops.map(workshopMapper::toDto);
    }

    @GetMapping("/{speakerId}")
    public Page<WorkshopDto> getAllBySpeaker(@PathVariable(name = "speakerId") UUID speakerId, @RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<Workshop> workshops = workshopService.getAllBySpeaker(speakerId, pageable);
        return workshops.map(workshopMapper::toDto);
    }

    @GetMapping("/{dateTime}")
    public Page<WorkshopDto> filterByDateTime(@PathVariable(name = "dateTime") ZonedDateTime dateTime, @RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<Workshop> workshops = workshopService.filterByDateTime(ZonedDateTime.now(), pageable);
        return workshops.map(workshopMapper::toDto);
    }

    @PostMapping
    public WorkshopDto create(@RequestBody @Valid WorkshopCreateDto createDto) {
        return Optional.ofNullable(createDto)
                .map(workshopMapper::fromCreateDto)
                .map(workshopService::create)
                .map(workshopMapper::toDto)
                .orElseThrow();
    }

    @PatchMapping("/{id}")
    public WorkshopDto update(@PathVariable(name = "id") Long id, @RequestBody @Valid WorkshopUpdateDto updateDto) {
        return Optional.ofNullable(updateDto)
                .map(workshopMapper::fromUpdateDto)
                .map(toUpdate -> workshopService.update(id, toUpdate))
                .map(workshopMapper::toDto)
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        workshopService.delete(id);
    }
}
