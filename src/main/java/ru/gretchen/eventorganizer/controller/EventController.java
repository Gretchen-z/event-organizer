package ru.gretchen.eventorganizer.controller;

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

// todo: swagger
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class EventController {
    private final EventMapper eventMapper;
    private final EventService eventService;

    @GetMapping("/{id}")
    public EventDto get(@PathVariable(name = "id") Long id) {
        return Optional.of(id)
                .map(eventService::get)
                .map(eventMapper::toDto)
                .orElseThrow(() -> new EventNotFoundException(id));
    }

    @GetMapping
    public Page<EventDto> getAll(@RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<Event> events = eventService.getAll(pageable);
        return events.map(eventMapper::toDto);
    }

    @GetMapping("/{userId}")
    public Page<EventDto> getAllByUserId(@PathVariable(name = "userId") UUID userId, @RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        Page<Event> events = eventService.getAllByUserId(userId, pageable);
        return events.map(eventMapper::toDto);
    }

    @GetMapping("/{dateTime}")
    public Page<EventDto> filterByDateTime(@PathVariable(name = "dateTime") ZonedDateTime dateTime, @RequestBody PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getLimit());
        dateTime = ZonedDateTime.now();
        Page<Event> events = eventService.filterByDateTime(dateTime, pageable);
        return events.map(eventMapper::toDto);
    }

    @PostMapping
    public EventDto create(@RequestBody @Valid EventCreateDto createDto) {
        return Optional.ofNullable(createDto)
                .map(eventMapper::fromCreateDto)
                .map(eventService::create)
                .map(eventMapper::toDto)
                .orElseThrow();
    }

    @PatchMapping("/{id}")
    public EventDto update(@PathVariable(name = "id") Long id, @RequestBody @Valid EventUpdateDto updateDto) {
        return Optional.ofNullable(updateDto)
                .map(eventMapper::fromUpdateDto)
                .map(toUpdate -> eventService.update(id, toUpdate))
                .map(eventMapper::toDto)
                .orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        eventService.delete(id);
    }
}
