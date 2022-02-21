package ru.gretchen.eventorganizer.integrarion;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import ru.gretchen.eventorganizer.model.dto.WorkshopCreateDto;
import ru.gretchen.eventorganizer.model.dto.WorkshopDto;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkshopControllerTest extends AbstractTest{
    @Test
    public void testCreateWorkshop() {
        WorkshopCreateDto request = getClassPathResourceAsObject("/request/user/create_workshop.json", new TypeReference<>() {
        });
        WorkshopDto excepted = getClassPathResourceAsObject("/excepted/user/created_workshop.json", new TypeReference<>() {
        });
        webTestClient
                .post()
                .uri(uriBuilder -> uriBuilder.path("/workshops").build())
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(WorkshopDto.class)
                .value(result -> assertThat(result)
                        .as("")
                        .usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(excepted)
                );
    }
}
