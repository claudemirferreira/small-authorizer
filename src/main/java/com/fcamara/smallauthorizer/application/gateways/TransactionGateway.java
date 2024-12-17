package com.fcamara.smallauthorizer.application.gateways;

import com.fcamara.smallauthorizer.domain.TransactionDomain;

public interface TransactionGateway {

    TransactionDomain save(TransactionDomain transactionDomain);

}
