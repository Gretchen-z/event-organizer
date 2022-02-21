package ru.gretchen.eventorganizer.integrarion;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import ru.gretchen.eventorganizer.model.dto.EventCreateDto;
import ru.gretchen.eventorganizer.model.dto.EventDto;

import static org.assertj.core.api.Assertions.assertThat;

public class EventControllerTest extends AbstractTest{
    @Test
    public void testCreateEvent() {
        EventCreateDto request = getClassPathResourceAsObject("/request/user/create_workshop.json", new TypeReference<>() {
        });
        EventDto excepted = getClassPathResourceAsObject("/excepted/user/created_event.json", new TypeReference<>() {
        });
        webTestClient
                .post()
                .uri(uriBuilder -> uriBuilder.path("/events").build())
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EventDto.class)
                .value(result -> assertThat(result)
                        .as("")
                        .usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(excepted)
                );
    }
}
