package com.fcamara.smallauthorizer.application.usecases;

import com.fcamara.smallauthorizer.domain.CardDomain;

public interface FindCardByNumberUsecase {

    CardDomain execute(final String number);
}
