package com.fcamara.smallauthorizer.infrastructure.gateways;

import com.fcamara.smallauthorizer.application.gateways.TransactionGateway;
import com.fcamara.smallauthorizer.domain.TransactionDomain;
import com.fcamara.smallauthorizer.infrastructure.persistence.entity.TransactionEntity;
import com.fcamara.smallauthorizer.infrastructure.persistence.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionRepositoryGatewayTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TransactionRepositoryGateway transactionRepositoryGateway;

    private TransactionDomain transactionDomain; // Objeto de domínio
    private TransactionEntity transactionEntity; // Objeto de entidade

    @BeforeEach
    public void setUp() {
        transactionDomain = new TransactionDomain(); // Inicialize conforme necessário
        transactionEntity = new TransactionEntity(); // Inicialize conforme necessário
    }

    @Test
    public void testSave_Success() {
        // Arrange
        when(modelMapper.map(transactionDomain, TransactionEntity.class)).thenReturn(transactionEntity);
        when(transactionRepository.save(transactionEntity)).thenReturn(transactionEntity);
        when(modelMapper.map(transactionEntity, TransactionDomain.class)).thenReturn(transactionDomain);

        // Act
        TransactionDomain result = transactionRepositoryGateway.save(transactionDomain);

        // Assert
        assertNotNull(result);
        assertEquals(transactionDomain, result);
        verify(modelMapper).map(transactionDomain, TransactionEntity.class);
        verify(transactionRepository).save(transactionEntity);
        verify(modelMapper).map(transactionEntity, TransactionDomain.class);
    }
}