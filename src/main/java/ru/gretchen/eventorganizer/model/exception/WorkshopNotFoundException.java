package ru.gretchen.eventorganizer.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class WorkshopNotFoundException extends RuntimeException {
    public WorkshopNotFoundException(UUID id) {
        super("Workshop not found: id=" + id);
    }
}
