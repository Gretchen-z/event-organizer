package ru.gretchen.eventorganizer.integrarion;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import ru.gretchen.eventorganizer.model.dto.UserCreateDto;
import ru.gretchen.eventorganizer.model.dto.UserDto;

import static org.assertj.core.api.Assertions.assertThat;

public class UserControllerTest extends AbstractTest {
    @Test
    public void testCreateUser() {
        UserCreateDto request = getClassPathResourceAsObject("/request/user/create_user.json", new TypeReference<>() {
        });
        UserDto excepted = getClassPathResourceAsObject("/excepted/user/created_user.json", new TypeReference<>() {
        });
        webTestClient
                .post()
                .uri(uriBuilder -> uriBuilder.path("/users").build())
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDto.class)
                .value(result -> assertThat(result)
                        .as("")
                        .usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(excepted)
                );
    }
}
