package com.fcamara.smallauthorizer.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TransactionDomainTest {

    @Test
    void testTransactionDomainConstructor() {
        // Arrange
        CardDomain cardDomain = new CardDomain(); // Inicialize conforme necessário
        BigDecimal value = new BigDecimal("100.00");
        LocalDateTime time = LocalDateTime.now();

        // Act
        TransactionDomain transaction = new TransactionDomain(cardDomain, value, time);

        // Assert
        assertNotNull(transaction);
        assertEquals(cardDomain, transaction.getCardDomain());
        assertEquals(value, transaction.getValue());
        assertEquals(time, transaction.getLastModifiedAt());
    }

    @Test
    void testBuilder() {
        // Arrange
        UUID id = UUID.randomUUID();
        LocalDateTime lastModifiedAt = LocalDateTime.now();
        BigDecimal value = new BigDecimal("200.00");
        CardDomain cardDomain = new CardDomain(); // Inicialize conforme necessário

        // Act
        TransactionDomain transaction = TransactionDomain.builder()
                .id(id)
                .lastModifiedAt(lastModifiedAt)
                .value(value)
                .cardDomain(cardDomain)
                .build();

        // Assert
        assertNotNull(transaction);
        assertEquals(id, transaction.getId());
        assertEquals(lastModifiedAt, transaction.getLastModifiedAt());
        assertEquals(value, transaction.getValue());
        assertEquals(cardDomain, transaction.getCardDomain());
    }

    @Test
    void testDefaultConstructor() {
        // Act
        TransactionDomain transaction = new TransactionDomain();

        // Assert
        assertNotNull(transaction);
        assertNull(transaction.getId());
        assertNull(transaction.getLastModifiedAt());
        assertNull(transaction.getValue());
        assertNull(transaction.getCardDomain());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        TransactionDomain transaction = new TransactionDomain();
        UUID id = UUID.randomUUID();
        LocalDateTime lastModifiedAt = LocalDateTime.now();
        BigDecimal value = new BigDecimal("150.00");
        CardDomain cardDomain = new CardDomain(); // Inicialize conforme necessário

        // Act
        transaction.setId(id);
        transaction.setLastModifiedAt(lastModifiedAt);
        transaction.setValue(value);
        transaction.setCardDomain(cardDomain);

        // Assert
        assertEquals(id, transaction.getId());
        assertEquals(lastModifiedAt, transaction.getLastModifiedAt());
        assertEquals(value, transaction.getValue());
        assertEquals(cardDomain, transaction.getCardDomain());
    }
}