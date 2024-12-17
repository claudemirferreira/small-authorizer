package com.fcamara.smallauthorizer.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CardInvalidPasswordException extends ResponseStatusException {

    public CardInvalidPasswordException(String reason) {
        super(HttpStatus.UNAUTHORIZED, reason);
    }

}