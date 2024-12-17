package com.fcamara.smallauthorizer.application.gateways;

import com.fcamara.smallauthorizer.domain.CardDomain;

public interface CardGateway {

    CardDomain save(CardDomain cardDomain);
    CardDomain findCardByNumber(String number);


}
