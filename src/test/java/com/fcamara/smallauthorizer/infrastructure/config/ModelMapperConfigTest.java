package com.fcamara.smallauthorizer.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class ModelMapperConfigTest {

    @Test
    void testModelMapperBean() {
        // Criar um contexto de aplicação apenas com a configuração específica
        try (var context = new AnnotationConfigApplicationContext(ModelMapperConfig.class)) {
            // Recuperar o bean ModelMapper do contexto
            ModelMapper modelMapper = context.getBean(ModelMapper.class);
            
            // Verificar se o bean não é nulo
            assertNotNull(modelMapper, "ModelMapper bean should not be null");
            
            // Verificar se o bean é do tipo esperado
            assertSame(ModelMapper.class, modelMapper.getClass(), "Bean should be an instance of ModelMapper");
        }
    }
}
