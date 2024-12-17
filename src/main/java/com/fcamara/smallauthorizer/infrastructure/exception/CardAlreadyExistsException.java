package com.fcamara.smallauthorizer.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class CardAlreadyExistsException extends ResponseStatusException {

    public CardAlreadyExistsException(String reason) {
        super(HttpStatus.UNAUTHORIZED, reason);
    }

}

