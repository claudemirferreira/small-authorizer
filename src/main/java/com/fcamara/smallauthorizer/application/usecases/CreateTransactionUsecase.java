package com.fcamara.smallauthorizer.application.usecases;

import com.fcamara.smallauthorizer.domain.TransactionDomain;

import java.math.BigDecimal;

public interface CreateTransactionUsecase {

    TransactionDomain execute(final String cardNumber, final BigDecimal transactionAmount, final String password);
}
