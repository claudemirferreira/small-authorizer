package com.fcamara.smallauthorizer.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OpenApiConfigTest {

    @Test
    void testCustomOpenAPI() {
        // Criar um contexto de aplicação apenas com a configuração específica
        try (var context = new AnnotationConfigApplicationContext(OpenApiConfig.class)) {
            // Recuperar o bean OpenAPI do contexto
            OpenAPI openAPI = context.getBean(OpenAPI.class);

            // Verificar se o bean não é nulo
            assertNotNull(openAPI, "OpenAPI bean should not be null");

            // Verificar as informações do OpenAPI
            Info info = openAPI.getInfo();
            assertNotNull(info, "Info object should not be null");
            assertEquals("Bank API VR", info.getTitle(), "Title should match the expected value");
            assertEquals("API for managing bank developed for Claudemir Ramos Ferreira", info.getDescription(), "Description should match the expected value");
            assertEquals("1.0.0", info.getVersion(), "Version should match the expected value");
        }
    }
}
