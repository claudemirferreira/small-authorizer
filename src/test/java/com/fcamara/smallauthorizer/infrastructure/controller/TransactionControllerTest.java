package com.fcamara.smallauthorizer.infrastructure.controller;

import com.fcamara.smallauthorizer.application.usecases.CreateTransactionUsecase;
import com.fcamara.smallauthorizer.infrastructure.controller.dto.TransactionResquestDTO;
import com.fcamara.smallauthorizer.domain.TransactionDomain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    @Mock
    private CreateTransactionUsecase createTransactionUsecase;

    @InjectMocks
    private TransactionController transactionController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    void shouldCreateNewTransaction() throws Exception {
        // Arrange: Mock the CreateTransactionUsecase to return a transaction with an ID
        UUID id = UUID.randomUUID();
        TransactionDomain transactionDomain = TransactionDomain
                .builder()
                .id(id)
                .lastModifiedAt(LocalDateTime.now())
                .value(BigDecimal.valueOf(100))
                .build();
        when(createTransactionUsecase.execute(any(), any(), any())).thenReturn(transactionDomain);

        mockMvc.perform(post("/transacoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"number\":\"123456\", \"amount\":100.0, \"password\":\"password\"}"))
                .andExpect(status().isCreated()) // Expecting 201 Created
                .andExpect(header().string("Location", "/transacoes/"+id)); // Expect Location header with new transaction ID
    }
}
