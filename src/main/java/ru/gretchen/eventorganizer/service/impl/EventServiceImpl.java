package ru.gretchen.eventorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gretchen.eventorganizer.model.entity.Event;
import ru.gretchen.eventorganizer.model.mapper.EventMapper;
import ru.gretchen.eventorganizer.repository.EventRepository;
import ru.gretchen.eventorganizer.service.EventService;

import java.time.ZonedDateTime;
import java.util.List;
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
    public List<Event> getAll() {
        return eventRepository.findAll();
    }

    @Override
    public List<Event> getAllByUserId(UUID userID) {
        return eventRepository.findAllByUsers_userId(userID);
    }

    @Override
    public List<Event> filterByDateTime(ZonedDateTime dateTime) {
        return eventRepository.findAllByDateTimeGreaterThan(dateTime);
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
