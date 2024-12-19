package com.fcamara.smallauthorizer.application.usecases.impl;

import com.fcamara.smallauthorizer.application.gateways.CardGateway;
import com.fcamara.smallauthorizer.application.gateways.TransactionGateway;
import com.fcamara.smallauthorizer.domain.CardDomain;
import com.fcamara.smallauthorizer.domain.TransactionDomain;
import com.fcamara.smallauthorizer.infrastructure.enums.TransactionErrorsEnum;
import com.fcamara.smallauthorizer.infrastructure.exception.CardInvalidPasswordException;
import com.fcamara.smallauthorizer.infrastructure.exception.InsufficientFoundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateTransactionUseCaseImplTest {

    @InjectMocks
    private CreateTransactionUseCaseImpl createTransactionUseCaseImpl;

    @Mock
    private CardGateway cardGateway;

    @Mock
    private TransactionGateway transactionGateway;

    private CardDomain cardDomain;

    @BeforeEach
    void setUp() {
        cardDomain = CardDomain.builder()
                .number("123456789")
                //.holderName("John Doe")
                .balance(BigDecimal.valueOf(1000))
                .password("1234")
                .build();
    }

    @Test
    void execute_ShouldCreateTransaction_WhenPasswordIsCorrectAndBalanceIsSufficient() {

        TransactionDomain transactionDomain = TransactionDomain
                .builder()
                .value(new BigDecimal("200"))
                .build();


        // Given: O cartão existe com saldo suficiente e senha correta
        when(cardGateway.findCardByNumber("123456789")).thenReturn(cardDomain);
        when(transactionGateway.save(any(TransactionDomain.class))).thenReturn(transactionDomain);

        // When: Chamando o método execute
        BigDecimal transactionAmount = BigDecimal.valueOf(200);
        TransactionDomain transaction = createTransactionUseCaseImpl.execute("123456789", transactionAmount, "1234");

        // Then: Verificando se a transação foi criada corretamente
        verify(cardGateway, times(1)).save(cardDomain); // O saldo do cartão deve ser atualizado
        verify(transactionGateway, times(1)).save(any(TransactionDomain.class)); // A transação deve ser salva

        assertNotNull(transaction);
        assertEquals(transactionAmount, transaction.getValue());
        assertEquals(cardDomain.getBalance(), BigDecimal.valueOf(800)); // Saldo atualizado
    }

    @Test
    void execute_ShouldThrowCardInvalidPasswordException_WhenPasswordIsIncorrect() {
        when(cardGateway.findCardByNumber("123456789")).thenReturn(cardDomain);
        CardInvalidPasswordException thrown = assertThrows(CardInvalidPasswordException.class, () -> {
            createTransactionUseCaseImpl.execute("123456789", BigDecimal.valueOf(200), "wrongPassword");
        });
        assertEquals(TransactionErrorsEnum.INVALID_PASSWORD.getKey(), thrown.getReason());
    }

    @Test
    void execute_ShouldThrowInsufficientFoundsException_WhenBalanceIsInsufficient() {
        when(cardGateway.findCardByNumber("123456789")).thenReturn(cardDomain);
        InsufficientFoundsException thrown = assertThrows(InsufficientFoundsException.class, () -> {
            createTransactionUseCaseImpl.execute("123456789", BigDecimal.valueOf(1500), "1234");
        });
        assertEquals(TransactionErrorsEnum.INSUFFICIENT_FOUNDS.getKey(), thrown.getReason());
    }
}
