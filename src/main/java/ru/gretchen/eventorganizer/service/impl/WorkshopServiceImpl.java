package ru.gretchen.eventorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gretchen.eventorganizer.model.entity.User;
import ru.gretchen.eventorganizer.model.entity.Workshop;
import ru.gretchen.eventorganizer.model.mapper.WorkshopMapper;
import ru.gretchen.eventorganizer.repository.WorkshopRepository;
import ru.gretchen.eventorganizer.service.UserService;
import ru.gretchen.eventorganizer.service.WorkshopService;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkshopServiceImpl implements WorkshopService {
    private final WorkshopRepository workshopRepository;
    private final WorkshopMapper workshopMapper;
    private final UserService userService;

    @Override
    public Workshop getAndInitialize(UUID id) {
        Workshop result = workshopRepository.findById(id).orElseThrow();
        Hibernate.initialize(result);
        Hibernate.initialize(result.getSpeaker());
        Hibernate.initialize(result.getEvent());
        return result;
    }

    @Override
    public Page<Workshop> getAll(Pageable pageable) {
        return workshopRepository.findAll(pageable);
    }

    @Override
    public Page<Workshop> getAllByEvent(UUID eventId, Pageable pageable) {
        return workshopRepository.findAllByEventId(eventId, pageable);
    }

    @Override
    public Page<Workshop> getAllBySpeaker(UUID speakerId, Pageable pageable) {
        return workshopRepository.findAllBySpeakerId(speakerId, pageable);
    }

    @Override
    public Page<Workshop> getAllByDateTime(ZonedDateTime dateTime, Pageable pageable) {
        return workshopRepository.findAllByDateTimeGreaterThan(ZonedDateTime.now(), pageable);
    }

    @Override
    @Transactional
    public Workshop create(Workshop workshopJson) {
        return workshopRepository.save(workshopJson);
    }

    @Override
    @Transactional
    public Workshop update(UUID id, Workshop workshopJson) {
        return Optional.of(id)
                .map(this::getAndInitialize)
                .map(current -> workshopMapper.merge(current, workshopJson))
                .map(workshopRepository::save)
                .orElseThrow();
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        final Workshop workshop = workshopRepository.findById(id).orElseThrow();
        workshopRepository.delete(workshop);
    }

    @Override
    public void assignSpeaker(UUID id, UUID speakerId) {
        Workshop workshop = getAndInitialize(id);
        User speaker = userService.getAndInitialize(speakerId);
        workshop.setSpeaker(speaker);
        update(id, workshop);
    }

    @Override
    public void deleteSpeaker(UUID id) {
        Workshop workshop = getAndInitialize(id);
        workshop.setSpeaker(null);
        update(id, workshop);
    }
}
