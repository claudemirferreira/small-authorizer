package com.fcamara.smallauthorizer.application.usecases;

import com.fcamara.smallauthorizer.domain.CardDomain;

public interface CreateCardUsecase {

    CardDomain execute(final CardDomain cardDomain);
}
