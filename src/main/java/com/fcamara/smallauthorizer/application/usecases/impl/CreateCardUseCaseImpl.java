package com.fcamara.smallauthorizer.application.usecases.impl;

import com.fcamara.smallauthorizer.application.usecases.CreateCardUsecase;
import com.fcamara.smallauthorizer.domain.CardDomain;
import org.springframework.stereotype.Service;

@Service
public class CreateCardUseCaseImpl implements CreateCardUsecase {

    @Override
    public CardDomain execute(CardDomain cardDomain) {
        return null;
    }
}
