package ru.gretchen.eventorganizer.model.mapper;

import org.mapstruct.*;
import ru.gretchen.eventorganizer.model.dto.TaskCreateDto;
import ru.gretchen.eventorganizer.model.dto.TaskDto;
import ru.gretchen.eventorganizer.model.dto.TaskUpdateDto;
import ru.gretchen.eventorganizer.model.entity.Task;

@Mapper
public interface TaskMapper {
    Task fromDto(TaskDto source);

    @Mapping(target = "id", ignore = true)
    Task fromCreateDto(TaskCreateDto source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreating", ignore = true)
    Task fromUpdateDto(TaskUpdateDto source);

    TaskDto toDto(Task source);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Task merge(@MappingTarget Task target, Task source);
}
