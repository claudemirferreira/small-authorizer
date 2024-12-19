package com.fcamara.smallauthorizer.application.usecases.impl;

import com.fcamara.smallauthorizer.application.gateways.CardGateway;
import com.fcamara.smallauthorizer.application.usecases.CreateCardUsecase;
import com.fcamara.smallauthorizer.domain.CardDomain;
import com.fcamara.smallauthorizer.infrastructure.exception.CardAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class CreateCardUseCaseImpl implements CreateCardUsecase {

    private final CardGateway cardGateway;

    @Override
    public CardDomain execute(final CardDomain cardDomain) {
        validCardAlreadyExists(cardDomain.getNumber());
        return cardGateway.save(cardDomain);
    }

    private void validCardAlreadyExists(String cardNumber) {
        var card = cardGateway.findCardByNumber(cardNumber);
        if(Objects.nonNull(card)){
            throw new CardAlreadyExistsException("");
        }

    }
}
