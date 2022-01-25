package ru.gretchen.eventorganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gretchen.eventorganizer.model.entity.Workshop;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface WorkshopRepository extends JpaRepository<Workshop, Long> {
    List<Workshop> findAllBySpeaker_UserId(UUID speakerId);

    List<Workshop> findAllByEvent_Id(Long eventId);

    List<Workshop> findAllByDateTimeGreaterThan(ZonedDateTime dateTime);
}
