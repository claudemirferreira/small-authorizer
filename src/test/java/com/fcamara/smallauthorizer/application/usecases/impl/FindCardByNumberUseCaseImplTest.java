package com.fcamara.smallauthorizer.application.usecases.impl;

import com.fcamara.smallauthorizer.application.gateways.CardGateway;
import com.fcamara.smallauthorizer.domain.CardDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FindCardByNumberUseCaseImplTest {

    @InjectMocks
    private FindCardByNumberUseCaseImpl findCardByNumberUseCaseImpl;

    @Mock
    private CardGateway cardGateway;

    private CardDomain cardDomain;

    @BeforeEach
    void setUp() {
        // Criando um CardDomain de exemplo para ser utilizado nos testes
        cardDomain = new CardDomain();
        cardDomain.setNumber("123456789");
        //cardDomain.setHolderName("John Doe");
    }

    @Test
    void execute_ShouldReturnCard_WhenCardExists() {
        // Given: Simulando o comportamento do CardGateway para encontrar o cartão
        when(cardGateway.findCardByNumber("123456789")).thenReturn(cardDomain);

        // When: Chamando o método execute
        CardDomain foundCard = findCardByNumberUseCaseImpl.execute("123456789");

        // Then: Verificando se o método findCardByNumber foi chamado no CardGateway
        verify(cardGateway, times(1)).findCardByNumber("123456789");

        // E verificando se o cartão encontrado é o esperado
        assertNotNull(foundCard);
        assertEquals(cardDomain.getNumber(), foundCard.getNumber());
        //assertEquals(cardDomain.getHolderName(), foundCard.getHolderName());
    }

    @Test
    void execute_ShouldReturnNull_WhenCardDoesNotExist() {
        // Given: Simulando o comportamento do CardGateway quando o cartão não é encontrado
        when(cardGateway.findCardByNumber("987654321")).thenReturn(null);

        // When: Chamando o método execute
        CardDomain foundCard = findCardByNumberUseCaseImpl.execute("987654321");

        // Then: Verificando se o método findCardByNumber foi chamado no CardGateway
        verify(cardGateway, times(1)).findCardByNumber("987654321");

        // E verificando se o retorno é null, já que o cartão não foi encontrado
        assertNull(foundCard);
    }

    @Test
    void execute_ShouldThrowException_WhenCardNumberIsNull() {
        // Given: Simulando o comportamento quando o número do cartão é null
        when(cardGateway.findCardByNumber(null)).thenThrow(new IllegalArgumentException("Card number cannot be null"));

        // When & Then: Verificando se uma exceção é lançada quando o número do cartão é null
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            findCardByNumberUseCaseImpl.execute(null);
        });

        // Then: Verificando se a mensagem da exceção está correta
        assertEquals("Card number cannot be null", thrown.getMessage());
    }
}
