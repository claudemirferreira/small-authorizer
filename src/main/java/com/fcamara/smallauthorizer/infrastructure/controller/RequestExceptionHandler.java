package com.fcamara.smallauthorizer.infrastructure.controller;

import com.fcamara.smallauthorizer.infrastructure.enums.TransactionErrorsEnum;
import com.fcamara.smallauthorizer.infrastructure.exception.CardAlreadyExistsException;
import com.fcamara.smallauthorizer.infrastructure.exception.CardInvalidPasswordException;
import com.fcamara.smallauthorizer.infrastructure.exception.CardNotFoundException;
import com.fcamara.smallauthorizer.infrastructure.exception.InsufficientFoundsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {

    private final View error;

    public RequestExceptionHandler(View error) {
        this.error = error;
    }

    @ExceptionHandler(InsufficientFoundsException.class)
    public ResponseEntity<Object> handleHttpMessageInsufficientFounds(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, TransactionErrorsEnum.INSUFFICIENT_FOUNDS.getKey(), new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(CardInvalidPasswordException.class)
    public ResponseEntity<Object> handleHttpMessageInvalidPassword(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, TransactionErrorsEnum.INVALID_PASSWORD.getKey(), new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<Object> handleHttpMessageCardNotFound(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, TransactionErrorsEnum.CARD_NOT_FOUND.getKey(), new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(CardAlreadyExistsException.class)
    public ResponseEntity<Object> handleHttpMessageCardAlreadyExistis(CardAlreadyExistsException ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return handleExceptionInternal(ex, ex.getBody().getDetail(), headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

}
