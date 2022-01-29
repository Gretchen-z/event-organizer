package ru.gretchen.eventorganizer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.gretchen.eventorganizer.model.entity.Workshop;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface WorkshopService {
    Workshop get(Long id);

    Page<Workshop> getAll(Pageable pageable);

    Page<Workshop> getAllByEvent(Long eventId, Pageable pageable);

    Page<Workshop> getAllBySpeaker(UUID speakerId, Pageable pageable);

    Page<Workshop> filterByDateTime(ZonedDateTime dateTime, Pageable pageable);

    Workshop create(Workshop workshopJson);

    Workshop update(Long id, Workshop workshopJson);

    void delete(Long id);
}
