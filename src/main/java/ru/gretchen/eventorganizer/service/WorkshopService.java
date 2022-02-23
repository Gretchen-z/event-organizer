package ru.gretchen.eventorganizer.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.gretchen.eventorganizer.model.entity.Workshop;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface WorkshopService {
    Workshop getAndInitialize(UUID id);

    Page<Workshop> getAll(Pageable pageable);

    Page<Workshop> getAllByEvent(UUID eventId, Pageable pageable);

    Page<Workshop> getAllBySpeaker(UUID speakerId, Pageable pageable);

    Page<Workshop> getAllByDateTime(ZonedDateTime dateTime, Pageable pageable);

    Workshop create(Workshop workshopJson);

    Workshop update(UUID id, Workshop workshopJson);

    void delete(UUID id);

    void assignSpeaker(UUID id, UUID speakerId);

    void deleteSpeaker(UUID id);
}
