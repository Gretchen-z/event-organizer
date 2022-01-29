package ru.gretchen.eventorganizer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gretchen.eventorganizer.model.entity.Workshop;

import java.time.ZonedDateTime;
import java.util.UUID;

@Repository
public interface WorkshopRepository extends JpaRepository<Workshop, Long> {

    Page<Workshop> findAllBySpeaker_UserId(UUID speakerId, Pageable pageable);

    Page<Workshop> findAllByEvent_Id(Long eventId, Pageable pageable);

    Page<Workshop> findAllByDateTimeGreaterThan(ZonedDateTime dateTime, Pageable pageable);
}
