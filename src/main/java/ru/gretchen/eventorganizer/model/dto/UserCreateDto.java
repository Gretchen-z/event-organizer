package ru.gretchen.eventorganizer.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.gretchen.eventorganizer.model.enumeration.Gender;

import java.time.LocalDate;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCreateDto {
    // todo: validator
    String firstName;
    String surname;
    String password;
    LocalDate dateOfBirth;
    Gender gender;
    String locality;
    String phoneNumber;
    String email;
}
