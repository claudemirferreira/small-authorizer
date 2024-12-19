package com.fcamara.smallauthorizer.application.usecases.impl;

import com.fcamara.smallauthorizer.application.gateways.CardGateway;
import com.fcamara.smallauthorizer.domain.CardDomain;
import com.fcamara.smallauthorizer.infrastructure.exception.CardAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCardUseCaseImplTest {

    @Mock
    private CardGateway cardGateway;

    @InjectMocks
    private CreateCardUseCaseImpl createCardUseCase;

    private CardDomain cardDomain;

    @BeforeEach
    public void setUp() {
        cardDomain = new CardDomain(); // Inicialize conforme necessário
        cardDomain.setNumber("1234567890123456"); // Defina um número de cartão
    }

    @Test
    void testExecute_CardDoesNotExist() {
        // Arrange
        when(cardGateway.findCardByNumber(cardDomain.getNumber())).thenReturn(null);
        when(cardGateway.save(cardDomain)).thenReturn(cardDomain);

        // Act
        CardDomain result = createCardUseCase.execute(cardDomain);

        // Assert
        assertNotNull(result);
        assertEquals(cardDomain, result);
        verify(cardGateway).findCardByNumber(cardDomain.getNumber());
        verify(cardGateway).save(cardDomain);
    }

    @Test
    void testExecute_CardAlreadyExists() {
        // Arrange
        when(cardGateway.findCardByNumber(cardDomain.getNumber())).thenReturn(cardDomain);

        // Act & Assert
        CardAlreadyExistsException exception = assertThrows(CardAlreadyExistsException.class, () -> {
            createCardUseCase.execute(cardDomain);
        });

        assertEquals("", exception.getReason());
        verify(cardGateway).findCardByNumber(cardDomain.getNumber());
        verify(cardGateway, never()).save(any());
    }
}