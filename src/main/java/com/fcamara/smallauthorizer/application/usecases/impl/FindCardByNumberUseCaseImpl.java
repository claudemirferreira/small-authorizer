package com.fcamara.smallauthorizer.application.usecases.impl;

import com.fcamara.smallauthorizer.application.gateways.CardGateway;
import com.fcamara.smallauthorizer.application.usecases.FindCardByNumberUsecase;
import com.fcamara.smallauthorizer.domain.CardDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindCardByNumberUseCaseImpl implements FindCardByNumberUsecase {
    private final CardGateway cardGateway;

    @Override
    public CardDomain execute(String number) {
        return cardGateway.findCardByNumber(number);
    }
}
