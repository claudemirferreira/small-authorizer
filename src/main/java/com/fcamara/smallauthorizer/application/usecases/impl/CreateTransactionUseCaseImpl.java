package com.fcamara.smallauthorizer.application.usecases.impl;

import com.fcamara.smallauthorizer.application.gateways.CardGateway;
import com.fcamara.smallauthorizer.application.gateways.TransactionGateway;
import com.fcamara.smallauthorizer.application.usecases.CreateTransactionUsecase;
import com.fcamara.smallauthorizer.domain.CardDomain;
import com.fcamara.smallauthorizer.domain.TransactionDomain;
import com.fcamara.smallauthorizer.infrastructure.enums.TransactionErrorsEnum;
import com.fcamara.smallauthorizer.infrastructure.exception.CardInvalidPasswordException;
import com.fcamara.smallauthorizer.infrastructure.exception.InsufficientFoundsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CreateTransactionUseCaseImpl implements CreateTransactionUsecase {

    private final CardGateway cardGateway;
    private final TransactionGateway transactionGateway;

    @Override
    public TransactionDomain execute(final String cardNumber, final BigDecimal transactionAmount, final String password) {
        final CardDomain cardDomain = cardGateway.findCardByNumber(cardNumber);
        final BigDecimal newBalance = subtractBalance(cardDomain.getBalance(), transactionAmount);

        validateCardPassword(password, cardDomain);

        //update card balance
        cardDomain.setBalance(newBalance);
        cardGateway.save(cardDomain);

        //create a new transaction
        final TransactionDomain newTransaction = TransactionDomain.builder()
                .cardDomain(cardDomain)
                .lastModifiedAt(LocalDateTime.now())
                .value(transactionAmount)
                .build();

        return transactionGateway.save(newTransaction);
    }

    private BigDecimal subtractBalance(final BigDecimal cardBalance, final BigDecimal transactionAmount) {
        return Optional.of(cardBalance.subtract(transactionAmount))
                .filter(balance -> balance.compareTo(BigDecimal.ZERO) >= 0)
                .orElseThrow(() -> new InsufficientFoundsException(TransactionErrorsEnum.INSUFFICIENT_FOUNDS.getKey()));
    }

    private void validateCardPassword(final String password, final CardDomain card) {
        Optional.of(password)
                .filter(p -> p.equals(card.getPassword()))
                .orElseThrow(() -> new CardInvalidPasswordException(TransactionErrorsEnum.INVALID_PASSWORD.getKey()));
    }

}
