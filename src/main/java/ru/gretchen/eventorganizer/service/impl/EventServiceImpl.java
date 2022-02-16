package ru.gretchen.eventorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gretchen.eventorganizer.model.entity.Event;
import ru.gretchen.eventorganizer.model.entity.User;
import ru.gretchen.eventorganizer.model.entity.Workshop;
import ru.gretchen.eventorganizer.model.mapper.EventMapper;
import ru.gretchen.eventorganizer.repository.EventRepository;
import ru.gretchen.eventorganizer.service.EventService;
import ru.gretchen.eventorganizer.service.UserService;
import ru.gretchen.eventorganizer.service.WorkshopService;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final WorkshopService workshopService;
    private final UserService userService;

    @Override
    public Event getAndInitialize(UUID id) {
        Event result = eventRepository.findById(id).orElseThrow();
        Hibernate.initialize(result);
        Hibernate.initialize(result.getWorkshops());
        Hibernate.initialize(result.getUsers());
        return result;
    }

    @Override
    public Page<Event> getAll(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }

    @Override
    public Page<Event> getAllByUserId(UUID userId, Pageable pageable) {
        return eventRepository.findAllByUsersId(userId, pageable);
    }

    @Override
    public Page<Event> getAllByDateTime(ZonedDateTime dateTime, Pageable pageable) {
        return eventRepository.findAllByDateTimeGreaterThan(ZonedDateTime.now(), pageable);
    }

    @Override
    @Transactional
    public Event create(Event eventJson) {
        return eventRepository.save(eventJson);
    }

    @Override
    @Transactional
    public Event update(UUID id, Event eventJson) {
        return Optional.of(id)
                .map(this::getAndInitialize)
                .map(current -> eventMapper.merge(current, eventJson))
                .map(eventRepository::save)
                .orElseThrow();
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        final Event event = eventRepository.findById(id).orElseThrow();
        eventRepository.delete(event);
    }

    @Override
    @Transactional
    public void assignWorkshop(UUID id, UUID workshopId) {
        Event event = getAndInitialize(id);
        Workshop workshop = workshopService.getAndInitialize(workshopId);
        event.addWorkshop(workshop);
        update(id, event);
    }

    @Override
    @Transactional
    public void deleteWorkshop(UUID id, UUID workshopId) {
        Event event = getAndInitialize(id);
        Workshop workshop = workshopService.getAndInitialize(workshopId);
        event.removeWorkshop(workshop);
        update(id, event);
    }

    @Override
    @Transactional
    public void assignUser(UUID id, UUID userId) {
        Event event = getAndInitialize(id);
        User user = userService.getAndInitialize(userId);
        event.addUser(user);
        user.addEvent(event);
        update(id, event);
    }

    @Override
    @Transactional
    public void deleteUser(UUID id, UUID userId) {
        Event event = getAndInitialize(id);
        User user = userService.getAndInitialize(userId);
        event.removeUser(user);
        user.removeEvent(event);
        update(id, event);
    }
}
