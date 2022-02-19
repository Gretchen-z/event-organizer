package ru.gretchen.eventorganizer.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EmailNotExistsException extends RuntimeException {
    public EmailNotExistsException(String email) {
        super("User with email " + email + "not found");
    }
}
