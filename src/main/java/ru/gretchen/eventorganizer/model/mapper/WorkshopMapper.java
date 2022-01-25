package ru.gretchen.eventorganizer.model.mapper;

import org.mapstruct.*;
import ru.gretchen.eventorganizer.model.dto.WorkshopCreateDto;
import ru.gretchen.eventorganizer.model.dto.WorkshopDto;
import ru.gretchen.eventorganizer.model.dto.WorkshopUpdateDto;
import ru.gretchen.eventorganizer.model.entity.Workshop;

@Mapper
public interface WorkshopMapper {
    @Mapping(target = "speaker", ignore = true)
    @Mapping(target = "event", ignore = true)
    Workshop fromDto(WorkshopDto source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "speaker", ignore = true)
    @Mapping(target = "event", ignore = true)
    Workshop fromCreateDto(WorkshopCreateDto source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "speaker", ignore = true)
    @Mapping(target = "event", ignore = true)
    Workshop fromUpdateDto(WorkshopUpdateDto source);

    @Mapping(target = "speakerId", source = "speaker.userId")
    @Mapping(target = "eventId", source = "event.id")
    WorkshopDto toDto(Workshop source);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Workshop merge(@MappingTarget Workshop target, Workshop source);
}
