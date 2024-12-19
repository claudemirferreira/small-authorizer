package com.fcamara.smallauthorizer.infrastructure.controller;

import com.fcamara.smallauthorizer.application.usecases.CreateTransactionUsecase;
import com.fcamara.smallauthorizer.domain.CardDomain;
import com.fcamara.smallauthorizer.domain.TransactionDomain;
import com.fcamara.smallauthorizer.infrastructure.controller.dto.TransactionResquestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private CreateTransactionUsecase createTransactionUsecase;

    @Mock
    private ModelMapper modelMapper;

    private TransactionResquestDTO transactionResquestDTO;
    private TransactionDomain transactionDomain;
    private UUID transactionId;
    private CardDomain cardDomain;

    @BeforeEach
    void setUp() {
        // Criação do DTO de transação
        transactionResquestDTO = new TransactionResquestDTO();
        transactionResquestDTO.setNumber("123456");
        transactionResquestDTO.setAmount(new BigDecimal("100.00"));
        transactionResquestDTO.setPassword("1234");

        // Criação do CardDomain
        cardDomain = new CardDomain();
        cardDomain.setNumber("123456");
        cardDomain.setPassword("1234");

        // Gerando um UUID para a transação
        transactionId = UUID.randomUUID();

        // Criação do TransactionDomain
        transactionDomain = new TransactionDomain();
        transactionDomain.setId(transactionId);
        transactionDomain.setValue(new BigDecimal("100.00"));
        transactionDomain.setCardDomain(cardDomain);
        transactionDomain.setLastModifiedAt(LocalDateTime.now());
    }

    @Test
    void newTransaction_ShouldReturnCreated() {
        // Given: Simulando a criação da transação no UseCase
        when(createTransactionUsecase.execute(transactionResquestDTO.getNumber(), 
                                               transactionResquestDTO.getAmount(), 
                                               transactionResquestDTO.getPassword()))
                .thenReturn(transactionDomain);

        // When: Chamando o método newTransaction no controller
        ResponseEntity<?> response = transactionController.newTransaction(transactionResquestDTO);

        // Then: Verificando se o status é 201 (Created) e a URI está correta
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("transaction/" + transactionDomain.getId(), response.getHeaders().getLocation().getPath());
        
        // Verificando se o método do UseCase foi chamado corretamente
        verify(createTransactionUsecase, times(1)).execute(transactionResquestDTO.getNumber(),
                transactionResquestDTO.getAmount(),
                transactionResquestDTO.getPassword());
    }

    @Test
    void newTransaction_ShouldReturnBadRequest_WhenUseCaseFails() {
        // Given: Simulando uma falha no UseCase
        when(createTransactionUsecase.execute(transactionResquestDTO.getNumber(), 
                                               transactionResquestDTO.getAmount(), 
                                               transactionResquestDTO.getPassword()))
                .thenThrow(new RuntimeException("Erro ao criar transação"));

        // When & Then: Verificando se o erro é tratado corretamente
        try {
            transactionController.newTransaction(transactionResquestDTO);
            fail("Deveria lançar uma exceção");
        } catch (RuntimeException e) {
            assertEquals("Erro ao criar transação", e.getMessage());
        }
    }
}
