package ru.gretchen.eventorganizer.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.gretchen.eventorganizer.model.enumeration.Gender;
import ru.gretchen.eventorganizer.model.enumeration.Role;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDto {
    UUID userId;
    String firstName;
    String surname;
    LocalDate dateOfBirth;
    Gender gender;
    String locality;
    String phoneNumber;
    String email;
    LocalDate registrationDate;
    Role role;
}
