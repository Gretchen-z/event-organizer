package ru.gretchen.eventorganizer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gretchen.eventorganizer.model.entity.Event;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByUsers_userId(UUID userID);

    List<Event> findAllByDateTimeGreaterThan(ZonedDateTime dateTime);
}
