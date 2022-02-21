package ru.gretchen.eventorganizer.integrarion;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import ru.gretchen.eventorganizer.model.dto.TaskCreateDto;
import ru.gretchen.eventorganizer.model.dto.TaskDto;

import static org.assertj.core.api.Assertions.assertThat;

public class TaskControllerTest extends AbstractTest{
    @Test
    public void testCreateTask() {
        TaskCreateDto request = getClassPathResourceAsObject("/request/user/create_task.json", new TypeReference<>() {
        });
        TaskDto excepted = getClassPathResourceAsObject("/excepted/user/created_task.json", new TypeReference<>() {
        });
        webTestClient
                .post()
                .uri(uriBuilder -> uriBuilder.path("/users").build())
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TaskDto.class)
                .value(result -> assertThat(result)
                        .as("")
                        .usingRecursiveComparison()
                        .ignoringFields("id")
                        .isEqualTo(excepted)
                );
    }
}
