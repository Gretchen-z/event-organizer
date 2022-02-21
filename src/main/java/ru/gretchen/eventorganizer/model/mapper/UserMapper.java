package ru.gretchen.eventorganizer.model.mapper;

import org.mapstruct.*;
import ru.gretchen.eventorganizer.model.dto.UserCreateDto;
import ru.gretchen.eventorganizer.model.dto.UserDto;
import ru.gretchen.eventorganizer.model.dto.UserRoleUpdateDto;
import ru.gretchen.eventorganizer.model.dto.UserUpdateDto;
import ru.gretchen.eventorganizer.model.entity.User;

@Mapper
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "events", ignore = true)
    User fromDto(UserDto source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "events", ignore = true)
    User fromCreateDto(UserCreateDto source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "role", ignore = true)
    User fromUpdateDto(UserUpdateDto source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "surname", ignore = true)
    @Mapping(target = "dateOfBirth", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "locality", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "events", ignore = true)
    User fromUpdateDto(UserRoleUpdateDto source);

    UserDto toDto(User source);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User merge(@MappingTarget User target, User source);
}
