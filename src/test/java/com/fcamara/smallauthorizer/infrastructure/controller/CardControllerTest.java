package com.fcamara.smallauthorizer.infrastructure.controller;

import com.fcamara.smallauthorizer.application.usecases.CreateCardUsecase;
import com.fcamara.smallauthorizer.application.usecases.FindCardByNumberUsecase;
import com.fcamara.smallauthorizer.domain.CardDomain;
import com.fcamara.smallauthorizer.infrastructure.controller.dto.CardRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CardControllerTest {

    @InjectMocks
    private CardController cardController;

    @Mock
    private CreateCardUsecase createCardUsecase;

    @Mock
    private FindCardByNumberUsecase findCardByNumberUsecase;

    @Mock
    private ModelMapper modelMapper;

    private CardRequestDTO cardRequestDTO;
    private CardDomain cardDomain;

    @BeforeEach
    void setUp() {
        cardRequestDTO = new CardRequestDTO();
        cardRequestDTO.setNumber("123456");

        cardDomain = new CardDomain();
        cardDomain.setNumber("123456");
    }

    @Test
    void createCard_ShouldReturnCreatedCard() {
        // Given
        when(modelMapper.map(cardRequestDTO, CardDomain.class)).thenReturn(cardDomain);
        when(createCardUsecase.execute(cardDomain)).thenReturn(cardDomain);
        when(modelMapper.map(cardDomain, CardRequestDTO.class)).thenReturn(cardRequestDTO);

        // When
        ResponseEntity<CardRequestDTO> response = cardController.createCard(cardRequestDTO);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("123456", response.getBody().getNumber());
        verify(createCardUsecase, times(1)).execute(cardDomain);
    }

    @Test
    void findCardByNumber_ShouldReturnCard() {
        // Given
        when(findCardByNumberUsecase.execute("123456")).thenReturn(cardDomain);
        when(modelMapper.map(cardDomain, CardRequestDTO.class)).thenReturn(cardRequestDTO);

        // When
        ResponseEntity<CardRequestDTO> response = cardController.findCardByNumber("123456");

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("123456", response.getBody().getNumber());
        verify(findCardByNumberUsecase, times(1)).execute("123456");
    }
}
