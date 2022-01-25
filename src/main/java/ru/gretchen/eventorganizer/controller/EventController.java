package ru.gretchen.eventorganizer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gretchen.eventorganizer.model.dto.EventCreateDto;
import ru.gretchen.eventorganizer.model.dto.EventDto;
import ru.gretchen.eventorganizer.model.dto.EventUpdateDto;
import ru.gretchen.eventorganizer.model.entity.Event;
import ru.gretchen.eventorganizer.model.exception.EventNotFoundException;
import ru.gretchen.eventorganizer.model.mapper.EventMapper;
import ru.gretchen.eventorganizer.service.EventService;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
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
    public List<EventDto> getAll() {
        List<Event> events = eventService.getAll();
        List<EventDto> eventDto = new ArrayList<>();
        for (Event event:events) {
            eventDto.add(eventMapper.toDto(event));
        }
        return eventDto;
    }

    @GetMapping("/{userId}")
    public List<EventDto> getAllByUserId(@PathVariable(name = "userId") UUID userId) {
        List<Event> events = eventService.getAllByUserId(userId);
        List<EventDto> eventDto = new ArrayList<>();
        for (Event event:events) {
            eventDto.add(eventMapper.toDto(event));
        }
        return eventDto;
    }

    @GetMapping("/{dateTime}")
    public List<EventDto> filterByDateTime(@PathVariable(name = "dateTime") ZonedDateTime dateTime) {
        dateTime = ZonedDateTime.now();
        List<Event> events = eventService.filterByDateTime(dateTime);
        List<EventDto> eventDto = new ArrayList<>();
        for (Event event:events) {
            eventDto.add(eventMapper.toDto(event));
        }
        return eventDto;
    }

    @PostMapping
    public EventDto create(@RequestBody EventCreateDto createDto) {
        return Optional.ofNullable(createDto)
                .map(eventMapper::fromCreateDto)
                .map(eventService::create)
                .map(eventMapper::toDto)
                .orElseThrow();
    }

    @PatchMapping("/{id}")
    public EventDto update(@PathVariable(name = "id") Long id, @RequestBody EventUpdateDto updateDto) {
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
