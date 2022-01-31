package ru.gretchen.eventorganizer.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Event organizer service",
        version = "1.0.0",
        description = "Event organizer"))
public class SwaggerConfiguration {
}
