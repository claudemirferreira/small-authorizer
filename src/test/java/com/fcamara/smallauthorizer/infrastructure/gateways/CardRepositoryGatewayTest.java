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
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CardRepositoryGatewayTest {

    @InjectMocks
    private CardRepositoryGateway cardRepositoryGateway;
    @Mock
    private CardRepository cardRepository;
    @Mock
    private ModelMapper modelMapper;
    private CardDomain cardDomain;
    private CardEntity cardEntity;

    @BeforeEach
    void setUp() {
        // Criação do objeto CardDomain
        cardDomain = CardDomain.builder()
                .number("123456789")
                .balance(BigDecimal.valueOf(1000))
                .password("1234")
                .build();

        // Criação da entidade CardEntity correspondente
        cardEntity = CardEntity.builder()
                .number("123456789")
                .balance(BigDecimal.valueOf(1000))
                .password("1234")
                .build();
    }

    @Test
    void save_ShouldSaveCardCorrectly() {
        // Given: Mockando o comportamento do ModelMapper e do cardRepository
        when(modelMapper.map(cardDomain, CardEntity.class)).thenReturn(cardEntity);
        when(cardRepository.save(cardEntity)).thenReturn(cardEntity);
        when(modelMapper.map(cardEntity, CardDomain.class)).thenReturn(cardDomain);

        // When: Chamando o método save
        CardDomain savedCard = cardRepositoryGateway.save(cardDomain);

        // Then: Verificando se o método save foi chamado corretamente
        verify(cardRepository, times(1)).save(cardEntity);
        assertNotNull(savedCard);
        assertEquals(cardDomain.getNumber(), savedCard.getNumber());
        assertEquals(cardDomain.getBalance(), savedCard.getBalance());
    }

    @Test
    void findCardByNumber_ShouldReturnCardDomain_WhenCardExists() {
        // Given: Mockando o comportamento do ModelMapper e do cardRepository
        when(cardRepository.findByNumber("123456789")).thenReturn(Optional.ofNullable(cardEntity));
        when(modelMapper.map(cardEntity, CardDomain.class)).thenReturn(cardDomain);

        // When: Chamando o método findCardByNumber
        CardDomain foundCard = cardRepositoryGateway.findCardByNumber("123456789");

        // Then: Verificando se o método findByNumber foi chamado corretamente
        verify(cardRepository, times(1)).findByNumber("123456789");
        assertNotNull(foundCard);
        assertEquals(cardDomain.getNumber(), foundCard.getNumber());
        assertEquals(cardDomain.getBalance(), foundCard.getBalance());
    }



    @Test
    public void testFindCardByNumber_CardNotFound() {
        when(cardRepository.findByNumber("1234567890123456")).thenReturn(Optional.empty());
        CardNotFoundException exception = assertThrows(CardNotFoundException.class, () -> {
            cardRepositoryGateway.findCardByNumber("1234567890123456");
        });
        assertEquals("...", exception.getReason());
        verify(cardRepository).findByNumber("1234567890123456");
        verify(modelMapper, never()).map(any(), any());
    }

}
