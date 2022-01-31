package ru.gretchen.eventorganizer.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(name = "User", description = "User")
public class UserDto {

    @Schema(description = "User id",
            required = true,
            pattern = "*.",
            maxLength = 36,
            minLength = 36)
    UUID userId;

    @Schema(description = "User name",
            required = true)
    String firstName;

    @Schema(description = "User surname",
            required = true)
    String surname;

    @Schema(description = "User date of birth")
    LocalDate dateOfBirth;

    @Schema(description = "User gender")
    Gender gender;

    @Schema(description = "User locality",
            required = true)
    String locality;

    @Schema(description = "User phone number",
            required = true)
    String phoneNumber;

    @Schema(description = "User email",
            required = true)
    String email;

    @Schema(description = "User date of registration")
    LocalDate registrationDate;

    @Schema(description = "User role")
    Role role;
}
