package com.fcamara.smallauthorizer.application.usecases.impl;

import com.fcamara.smallauthorizer.application.gateways.CardGateway;
import com.fcamara.smallauthorizer.domain.CardDomain;
import com.fcamara.smallauthorizer.infrastructure.exception.CardAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateCardUseCaseImplTest {

    @Mock
    private CardGateway cardGateway;

    @InjectMocks
    private CreateCardUseCaseImpl createCardUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_ShouldCreateCard_WhenCardDoesNotExist() {
        // Arrange
        CardDomain cardDomain = CardDomain
                .builder()
                .balance(new BigDecimal("1000"))
                .number("1")
                .password("1")
                .build();
        when(cardGateway.accountExist(cardDomain.getNumber())).thenReturn(false);
        when(cardGateway.save(cardDomain)).thenReturn(cardDomain);

        // Act
        CardDomain result = createCardUseCase.execute(cardDomain);

        // Assert
        assertNotNull(result);
        assertEquals(cardDomain, result);
        verify(cardGateway).accountExist(cardDomain.getNumber());
        verify(cardGateway).save(cardDomain);
    }

    @Test
    void execute_ShouldThrowException_WhenCardAlreadyExists() {
        // Arrange
        CardDomain cardDomain = CardDomain
                .builder()
                .balance(new BigDecimal("1000"))
                .number("1")
                .password("1")
                .build();
        when(cardGateway.accountExist(cardDomain.getNumber())).thenReturn(true);

        // Act & Assert
        assertThrows(CardAlreadyExistsException.class, () -> createCardUseCase.execute(cardDomain));
        verify(cardGateway).accountExist(cardDomain.getNumber());
        verify(cardGateway, never()).save(any(CardDomain.class));
    }
}
