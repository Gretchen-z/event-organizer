package ru.gretchen.eventorganizer.service;

import ru.gretchen.eventorganizer.model.entity.Event;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface EventService {
    Event get(Long id);

    List<Event> getAll();

    List<Event> getAllByUserId(UUID userID);

    List<Event> filterByDateTime(ZonedDateTime dateTime);

    Event create(Event eventJson);

    Event update(Long id, Event eventJson);

    void delete(Long id);
}
