package ru.gretchen.eventorganizer.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gretchen.eventorganizer.model.entity.Workshop;
import ru.gretchen.eventorganizer.model.mapper.WorkshopMapper;
import ru.gretchen.eventorganizer.repository.WorkshopRepository;
import ru.gretchen.eventorganizer.service.WorkshopService;

import java.time.ZonedDateTime;
import java.util.List;
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
    public List<Workshop> getAll() {
        return workshopRepository.findAll();
    }

    @Override
    public List<Workshop> getAllByEvent(Long eventId) {
        return workshopRepository.findAllByEvent_Id(eventId);
    }

    @Override
    public List<Workshop> getAllBySpeaker(UUID speakerId) {
        return workshopRepository.findAllBySpeaker_UserId(speakerId);
    }

    @Override
    public List<Workshop> filterByDateTime(ZonedDateTime dateTime) {
        return workshopRepository.findAllByDateTimeGreaterThan(dateTime);
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
