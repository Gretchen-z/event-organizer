package ru.gretchen.eventorganizer.service;

import ru.gretchen.eventorganizer.model.entity.Workshop;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface WorkshopService {
    Workshop get(Long id);

    List<Workshop> getAll();

    List<Workshop> getAllByEvent(Long eventId);

    List<Workshop> getAllBySpeaker(UUID speakerId);

    List<Workshop> filterByDateTime(ZonedDateTime dateTime);

    Workshop create(Workshop workshopJson);

    Workshop update(Long id, Workshop workshopJson);

    void delete(Long id);
}
