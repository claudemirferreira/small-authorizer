package com.fcamara.smallauthorizer.application.usecases.impl;

import com.fcamara.smallauthorizer.application.usecases.FindCardByNumberUsecase;
import com.fcamara.smallauthorizer.domain.CardDomain;
import org.springframework.stereotype.Service;

@Service
public class FindCardByNumberUseCaseImpl implements FindCardByNumberUsecase {

    @Override
    public CardDomain execute(String number) {
        return null;
    }
}
