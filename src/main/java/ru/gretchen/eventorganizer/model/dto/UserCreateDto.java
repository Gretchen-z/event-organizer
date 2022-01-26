package ru.gretchen.eventorganizer.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import ru.gretchen.eventorganizer.model.enumeration.Gender;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCreateDto {

    @NotBlank(message = "Поле \"Имя\" не может быть пустым")
    @Size(max = 50, message = "Имя должно содердать не более 50 символов")
    String firstName;

    @NotBlank(message = "Поле \"Фамилия\" не может быть пустым")
    @Size(max = 50, message = "Фамилия должна содержать не более 50 символов")
    String surname;

    @NotBlank(message = "Поле \"Пароль\" не может быть пустым")
    @Size(min = 3, max = 50, message = "Пароль должен содержать не менее 3 и не более 50 символов")
    String password;

    @Past(message = "Введите корректную дату рождения")
    LocalDate dateOfBirth;

    Gender gender;

    @NotBlank(message = "Поле \"Населённый пункт\" не может быть пустым")
    @Size(max = 50, message = "Поле \"Населённый пункт\" не может содержать более 50 символов")
    String locality;

    @NotBlank(message = "Поле \"Номер телефона\" не может быть пустым")
    @Size(max = 50, message = "Поле \"Номер телефона\" не может содержать более 50 символов")
    String phoneNumber;

    @NotBlank(message = "Поле \"Email\" не может быть пустым")
    @Email(message = "Введите корректный email")
    @Size(max = 50, message = "Поле \"Email\" не может содержать более 50 символов")
    String email;
}
