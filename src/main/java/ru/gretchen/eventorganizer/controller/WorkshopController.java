package ru.gretchen.eventorganizer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gretchen.eventorganizer.model.dto.WorkshopCreateDto;
import ru.gretchen.eventorganizer.model.dto.WorkshopDto;
import ru.gretchen.eventorganizer.model.dto.WorkshopUpdateDto;
import ru.gretchen.eventorganizer.model.entity.Workshop;
import ru.gretchen.eventorganizer.model.exception.WorkshopNotFoundException;
import ru.gretchen.eventorganizer.model.mapper.WorkshopMapper;
import ru.gretchen.eventorganizer.service.WorkshopService;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
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
    public List<WorkshopDto> getAll() {
        List<Workshop> workshops = workshopService.getAll();
        List<WorkshopDto> workshopDto = new ArrayList<>();
        for (Workshop workshop:workshops) {
            workshopDto.add(workshopMapper.toDto(workshop));
        }
        return workshopDto;
    }

    @GetMapping("/{eventId}")
    public List<WorkshopDto> getAllByEvent(@PathVariable(name = "eventId") Long eventId) {
        List<Workshop> workshops = workshopService.getAllByEvent(eventId);
        List<WorkshopDto> workshopDto = new ArrayList<>();
        for (Workshop workshop:workshops) {
            workshopDto.add(workshopMapper.toDto(workshop));
        }
        return workshopDto;
    }

    @GetMapping("/{speakerId}")
    public List<WorkshopDto> getAllBySpeaker(@PathVariable(name = "speakerId") UUID speakerId) {
        List<Workshop> workshops = workshopService.getAllBySpeaker(speakerId);
        List<WorkshopDto> workshopDto = new ArrayList<>();
        for (Workshop workshop:workshops) {
            workshopDto.add(workshopMapper.toDto(workshop));
        }
        return workshopDto;
    }

    @GetMapping("/{dateTime}")
    public List<WorkshopDto> filterByDateTime(@PathVariable(name = "dateTime") ZonedDateTime dateTime) {
        List<Workshop> workshops = workshopService.filterByDateTime(ZonedDateTime.now());
        List<WorkshopDto> workshopDto = new ArrayList<>();
        for (Workshop workshop:workshops) {
            workshopDto.add(workshopMapper.toDto(workshop));
        }
        return workshopDto;
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
