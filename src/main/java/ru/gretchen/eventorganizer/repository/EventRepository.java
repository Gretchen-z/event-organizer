package ru.gretchen.eventorganizer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gretchen.eventorganizer.model.entity.Event;

import java.time.ZonedDateTime;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findAllByUsers_userId(UUID userID, Pageable pageable);

    Page<Event> findAllByDateTimeGreaterThan(ZonedDateTime dateTime, Pageable pageable);
}
