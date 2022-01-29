package ru.gretchen.eventorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.gretchen.eventorganizer.model.entity.Workshop;
import ru.gretchen.eventorganizer.model.mapper.WorkshopMapper;
import ru.gretchen.eventorganizer.repository.WorkshopRepository;
import ru.gretchen.eventorganizer.service.WorkshopService;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkshopServiceImpl implements WorkshopService {
    private final WorkshopRepository workshopRepository;
    private final WorkshopMapper workshopMapper;

    @Override
    public Workshop get(Long id) {
        return workshopRepository.getById(id);
    }

    @Override
    public Page<Workshop> getAll(Pageable pageable) {
        return workshopRepository.findAll(pageable);
    }

    @Override
    public Page<Workshop> getAllByEvent(Long eventId, Pageable pageable) {
        return workshopRepository.findAllByEvent_Id(eventId, pageable);
    }

    @Override
    public Page<Workshop> getAllBySpeaker(UUID speakerId, Pageable pageable) {
        return workshopRepository.findAllBySpeaker_UserId(speakerId, pageable);
    }

    @Override
    public Page<Workshop> filterByDateTime(ZonedDateTime dateTime, Pageable pageable) {
        return workshopRepository.findAllByDateTimeGreaterThan(dateTime, pageable);
    }

    @Override
    public Workshop create(Workshop workshopJson) {
        return workshopRepository.save(workshopJson);
    }

    @Override
    public Workshop update(Long id, Workshop workshopJson) {
        return Optional.of(id)
                .map(this::get)
                .map(current -> workshopMapper.merge(current, workshopJson))
                .map(workshopRepository::save)
                .orElseThrow();
    }

    @Override
    public void delete(Long id) {
        workshopRepository.deleteById(id);
    }
}
