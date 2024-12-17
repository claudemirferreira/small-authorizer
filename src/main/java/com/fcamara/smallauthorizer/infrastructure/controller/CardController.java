package com.fcamara.smallauthorizer.infrastructure.controller;

import com.fcamara.smallauthorizer.application.usecases.CreateCardUsecase;
import com.fcamara.smallauthorizer.application.usecases.FindCardByNumberUsecase;
import com.fcamara.smallauthorizer.domain.CardDomain;
import com.fcamara.smallauthorizer.infrastructure.controller.dto.CardRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = "/cartoes")
@RequiredArgsConstructor
public class CardController {

    private final ModelMapper modelMapper;
    private final CreateCardUsecase createCardUsecase;
    public final FindCardByNumberUsecase findCardByNumberUsecase;

    @PostMapping()
    public ResponseEntity<CardRequestDTO> createCard(@Valid @RequestBody final CardRequestDTO cardRequestDTO) {
        final var dardDomain = modelMapper.map(cardRequestDTO, CardDomain.class);
        final var newCard = createCardUsecase.execute(dardDomain);
        return ResponseEntity.created(URI.create("/cartoes/" + newCard.getNumber())).body(modelMapper.map(newCard, CardRequestDTO.class));
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<CardRequestDTO> findCardByNumber(@PathVariable("numeroCartao") final String number) {
        var cardDomain = findCardByNumberUsecase.execute(number);
        return ResponseEntity.ok(modelMapper.map(cardDomain, CardRequestDTO.class));
    }

}
