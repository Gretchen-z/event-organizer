package ru.gretchen.eventorganizer.model.mapper;

import org.mapstruct.*;
import ru.gretchen.eventorganizer.model.dto.EventCreateDto;
import ru.gretchen.eventorganizer.model.dto.EventDto;
import ru.gretchen.eventorganizer.model.dto.EventUpdateDto;
import ru.gretchen.eventorganizer.model.entity.Event;

@Mapper
public interface EventMapper {
    @Mapping(target = "workshops", ignore = true)
    @Mapping(target = "users", ignore = true)
    Event fromDto(EventDto source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "workshops", ignore = true)
    @Mapping(target = "users", ignore = true)
    Event fromCreateDto(EventCreateDto source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "workshops", ignore = true)
    @Mapping(target = "users", ignore = true)
    Event fromUpdateDto(EventUpdateDto source);

    EventDto toDto(Event source);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Event merge(@MappingTarget Event target, Event source);
}
