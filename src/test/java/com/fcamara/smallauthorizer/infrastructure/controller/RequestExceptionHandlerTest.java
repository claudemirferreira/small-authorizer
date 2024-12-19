package com.fcamara.smallauthorizer.infrastructure.controller;

import com.fcamara.smallauthorizer.infrastructure.enums.TransactionErrorsEnum;
import com.fcamara.smallauthorizer.infrastructure.exception.CardAlreadyExistsException;
import com.fcamara.smallauthorizer.infrastructure.exception.CardInvalidPasswordException;
import com.fcamara.smallauthorizer.infrastructure.exception.CardNotFoundException;
import com.fcamara.smallauthorizer.infrastructure.exception.InsufficientFoundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.View;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequestExceptionHandlerTest {

    private RequestExceptionHandler requestExceptionHandler;

    @Mock
    private View errorView;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        requestExceptionHandler = new RequestExceptionHandler(errorView);
    }

    @Test
    void testHandleHttpMessageInsufficientFounds() {
        InsufficientFoundsException ex = new InsufficientFoundsException("");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<Object> response = requestExceptionHandler.handleHttpMessageInsufficientFounds(ex, request);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(TransactionErrorsEnum.INSUFFICIENT_FOUNDS.getKey(), response.getBody());
    }

    @Test
    void testHandleHttpMessageInvalidPassword() {
        CardInvalidPasswordException ex = new CardInvalidPasswordException("");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<Object> response = requestExceptionHandler.handleHttpMessageInvalidPassword(ex, request);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(TransactionErrorsEnum.INVALID_PASSWORD.getKey(), response.getBody());
    }

    @Test
    void testHandleHttpMessageCardNotFound() {
        CardNotFoundException ex = new CardNotFoundException("");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<Object> response = requestExceptionHandler.handleHttpMessageCardNotFound(ex, request);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(TransactionErrorsEnum.CARD_NOT_FOUND.getKey(), response.getBody());
    }

}