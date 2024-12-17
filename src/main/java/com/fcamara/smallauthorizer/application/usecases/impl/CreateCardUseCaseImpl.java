package com.fcamara.smallauthorizer.application.usecases.impl;

import com.fcamara.smallauthorizer.application.gateways.CardGateway;
import com.fcamara.smallauthorizer.application.usecases.CreateCardUsecase;
import com.fcamara.smallauthorizer.domain.CardDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CreateCardUseCaseImpl implements CreateCardUsecase {

    private final CardGateway cardGateway;

    @Override
    public CardDomain execute(final CardDomain cardDomain) {
        return cardGateway.save(cardDomain);
    }
}
