package ru.gretchen.eventorganizer.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.gretchen.eventorganizer.model.enumeration.Gender;
import ru.gretchen.eventorganizer.model.enumeration.Role;

import java.time.LocalDate;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUpdateDto {
    String firstName;
    String surname;
    LocalDate dateOfBirth;
    Gender gender;
    String locality;
    String phoneNumber;
    String email;
    Role role;
}
