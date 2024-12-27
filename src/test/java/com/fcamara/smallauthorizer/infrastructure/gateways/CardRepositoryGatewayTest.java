package com.fcamara.smallauthorizer.infrastructure.gateways;

import com.fcamara.smallauthorizer.domain.CardDomain;
import com.fcamara.smallauthorizer.infrastructure.exception.CardNotFoundException;
import com.fcamara.smallauthorizer.infrastructure.persistence.entity.CardEntity;
import com.fcamara.smallauthorizer.infrastructure.persistence.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CardRepositoryGatewayTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CardRepositoryGateway cardRepositoryGateway;

    private CardDomain cardDomain;
    private CardEntity cardEntity;

    @BeforeEach
    void setUp() {
        // Set up test data
        cardDomain = CardDomain
                .builder()
                .id(UUID.randomUUID())
                .balance(BigDecimal.valueOf(1000))
                .password("password")
                .lastModifiedAt(LocalDateTime.now())
                .build();
                //new CardDomain("123456", "password", 1000.00);
        cardEntity = CardEntity
                .builder()
                .balance(BigDecimal.valueOf(1000))
                .number("1")
                .password("password")
                .transactions(List.of())
                .build();
    }

    @Test
    void save_shouldReturnSavedCard() {
        // Arrange: Mock the repository and model mapper behavior
        when(cardRepository.save(any(CardEntity.class))).thenReturn(cardEntity);
        when(modelMapper.map(any(CardDomain.class), eq(CardEntity.class))).thenReturn(cardEntity);
        when(modelMapper.map(any(CardEntity.class), eq(CardDomain.class))).thenReturn(cardDomain);

        // Act: Call the save method
        CardDomain result = cardRepositoryGateway.save(cardDomain);

        // Assert: Verify that the result is as expected
        assertNotNull(result);
        assertEquals(cardDomain.getNumber(), result.getNumber());
        assertEquals(cardDomain.getPassword(), result.getPassword());
        assertEquals(cardDomain.getBalance(), result.getBalance());

        // Verify interactions with mocks
        verify(cardRepository, times(1)).save(any(CardEntity.class));
        verify(modelMapper, times(1)).map(any(CardDomain.class), eq(CardEntity.class));
        verify(modelMapper, times(1)).map(any(CardEntity.class), eq(CardDomain.class));
    }

    @Test
    void findCardByNumber_shouldReturnCardDomain() {
        // Arrange: Mock the repository and model mapper behavior
        when(cardRepository.findByNumber("123456")).thenReturn(java.util.Optional.of(cardEntity));
        when(modelMapper.map(any(CardEntity.class), eq(CardDomain.class))).thenReturn(cardDomain);

        // Act: Call the findCardByNumber method
        CardDomain result = cardRepositoryGateway.findCardByNumber("123456");

        // Assert: Verify that the result is as expected
        assertNotNull(result);
        assertEquals(cardDomain.getNumber(), result.getNumber());
        assertEquals(cardDomain.getPassword(), result.getPassword());
        assertEquals(cardDomain.getBalance(), result.getBalance());

        // Verify interactions with mocks
        verify(cardRepository, times(1)).findByNumber("123456");
        verify(modelMapper, times(1)).map(any(CardEntity.class), eq(CardDomain.class));
    }

    @Test
    void findCardByNumber_shouldThrowCardNotFoundException_whenCardNotFound() {
        // Arrange: Mock the repository to return empty
        when(cardRepository.findByNumber("123456")).thenReturn(java.util.Optional.empty());

        // Act & Assert: Verify that the exception is thrown
        CardNotFoundException exception = assertThrows(CardNotFoundException.class, () -> {
            cardRepositoryGateway.findCardByNumber("123456");
        });

        assertEquals("...", exception.getReason());

        // Verify interactions with mocks
        verify(cardRepository, times(1)).findByNumber("123456");
    }

    @Test
    void accountExist_shouldReturnTrueWhenCardExists() {
        // Arrange: Mock the repository behavior
        when(cardRepository.findByNumber("123456")).thenReturn(java.util.Optional.of(cardEntity));

        // Act: Call the accountExist method
        boolean result = cardRepositoryGateway.accountExist("123456");

        // Assert: Verify that the result is true
        assertTrue(result);

        // Verify interactions with mocks
        verify(cardRepository, times(1)).findByNumber("123456");
    }

    @Test
    void accountExist_shouldReturnFalseWhenCardDoesNotExist() {
        // Arrange: Mock the repository to return empty
        when(cardRepository.findByNumber("123456")).thenReturn(java.util.Optional.empty());

        // Act: Call the accountExist method
        boolean result = cardRepositoryGateway.accountExist("123456");

        // Assert: Verify that the result is false
        assertFalse(result);

        // Verify interactions with mocks
        verify(cardRepository, times(1)).findByNumber("123456");
    }
}
