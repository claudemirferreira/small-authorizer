package com.fcamara.smallauthorizer.application.usecases.impl;

import com.fcamara.smallauthorizer.application.gateways.CardGateway;
import com.fcamara.smallauthorizer.application.usecases.CreateCardUsecase;
import com.fcamara.smallauthorizer.domain.CardDomain;
import com.fcamara.smallauthorizer.infrastructure.exception.CardAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        if(cardGateway.accountExist(cardNumber)){
            throw new CardAlreadyExistsException("");
        }

    }
}
