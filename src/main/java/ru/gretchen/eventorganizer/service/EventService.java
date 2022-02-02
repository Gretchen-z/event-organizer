package ru.gretchen.eventorganizer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.gretchen.eventorganizer.model.entity.Event;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface EventService {
    Event get(UUID id);

    Page<Event> getAll(Pageable pageable);

    Page<Event> getAllByUserId(UUID userID, Pageable pageable);

    Page<Event> filterByDateTime(ZonedDateTime dateTime, Pageable pageable);

    Event create(Event eventJson);

    Event update(UUID id, Event eventJson);

    void delete(UUID id);
}
