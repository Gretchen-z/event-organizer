package ru.gretchen.eventorganizer.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.gretchen.eventorganizer.model.dto.UserDto;
import ru.gretchen.eventorganizer.model.entity.Event;
import ru.gretchen.eventorganizer.model.entity.User;
import ru.gretchen.eventorganizer.model.entity.Workshop;
import ru.gretchen.eventorganizer.model.mapper.UserMapper;
import ru.gretchen.eventorganizer.service.EventService;
import ru.gretchen.eventorganizer.service.WorkshopService;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermissionEvaluatorImpl implements PermissionEvaluator {

    private final WorkshopService workshopService;
    private final EventService eventService;
    private final UserMapper userMapper;

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if ((authentication == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }

        return handleUserPermission(authentication, targetType, (UUID) targetId, (String) permission);
    }

    private boolean handleUserPermission(Authentication authentication, String targetType, UUID targetId, String permission) {
        UserDto principal = (UserDto) authentication.getPrincipal();

        switch (targetType){
            case "USER": return checkPermissionForUserArea(principal, targetId);
            case "EVENT": return chekEventPermission(principal, targetId);
            case "WORKSHOP_GET": return checkGetWorkshopPermission(principal, targetId);
            case "WORKSHOP_EVENT": return checkGetAllByEventWorkshopPermission(principal, targetId);
        }

        return false;
    }

    private boolean checkPermissionForUserArea(UserDto principal, UUID targetId) {
        return principal.getId().equals(targetId);
    }

    private boolean chekEventPermission(UserDto principal, UUID targetId){
        return principal.getId().equals(targetId);
    }

    private boolean checkGetWorkshopPermission(UserDto principal, UUID targetId){
        Workshop workshop = workshopService.getAndInitialize(targetId);
        User speaker = workshop.getSpeaker();
        return principal.getId().equals(speaker.getId());
    }

    private boolean checkGetAllByEventWorkshopPermission(UserDto principal, UUID targetId){
        Event event = eventService.getAndInitialize(targetId);
        Set<User> users = event.getUsers();
        User user = userMapper.fromDto(principal);
        return users.contains(user);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        throw new UnsupportedOperationException();
    }
}
