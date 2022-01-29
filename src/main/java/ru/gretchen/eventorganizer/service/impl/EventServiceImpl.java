package ru.gretchen.eventorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.gretchen.eventorganizer.model.entity.Event;
import ru.gretchen.eventorganizer.model.mapper.EventMapper;
import ru.gretchen.eventorganizer.repository.EventRepository;
import ru.gretchen.eventorganizer.service.EventService;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public Event get(Long id) {
        return eventRepository.getById(id);
    }

    @Override
    public Page<Event> getAll(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    @Override
    public Page<Event> getAllByUserId(UUID userID, Pageable pageable) {
        return eventRepository.findAllByUsers_userId(userID, pageable);
    }

    @Override
    public Page<Event> filterByDateTime(ZonedDateTime dateTime, Pageable pageable) {
        return eventRepository.findAllByDateTimeGreaterThan(dateTime, pageable);
    }

    @Override
    public Event create(Event eventJson) {
        return eventRepository.save(eventJson);
    }

    @Override
    public Event update(Long id, Event eventJson) {
        return Optional.of(id)
                .map(this::get)
                .map(current -> eventMapper.merge(current, eventJson))
                .map(eventRepository::save)
                .orElseThrow();
    }

    @Override
    public void delete(Long id) {
        eventRepository.deleteById(id);
    }
}
