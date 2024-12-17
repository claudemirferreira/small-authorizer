package com.fcamara.smallauthorizer.infrastructure.gateways;

import com.fcamara.smallauthorizer.application.gateways.CardGateway;
import com.fcamara.smallauthorizer.domain.CardDomain;
import com.fcamara.smallauthorizer.infrastructure.persistence.entity.CardEntity;
import com.fcamara.smallauthorizer.infrastructure.persistence.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardRepositoryGateway implements CardGateway {

    private final CardRepository cardRepository;
    private final ModelMapper modelMapper;

    @Override
    public CardDomain save(CardDomain cardDomain) {
        var cardSaved = cardRepository.save(modelMapper.map(cardDomain, CardEntity.class));
        return modelMapper.map(cardSaved, CardDomain.class);
    }

    @Override
    public CardDomain findCardByNumber(String number) {
        var entity = cardRepository.findByNumber(number);
        return modelMapper.map(entity, CardDomain.class);
    }
}
