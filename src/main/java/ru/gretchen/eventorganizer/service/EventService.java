package ru.gretchen.eventorganizer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.gretchen.eventorganizer.model.entity.Event;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface EventService {
    Event getAndInitialize(UUID id);

    Page<Event> getAll(Pageable pageable);

    Page<Event> getAllByUserId(UUID userId, Pageable pageable);

    Page<Event> getAllByDateTime(ZonedDateTime dateTime, Pageable pageable);

    Event create(Event eventJson);

    Event update(UUID id, Event eventJson);

    void delete(UUID id);

    void assignWorkshop(UUID id, UUID workshopId);

    void deleteWorkshop(UUID id, UUID workshopId);

    void assignUser(UUID id, UUID userId);

    void deleteUser(UUID id, UUID userId);
}
