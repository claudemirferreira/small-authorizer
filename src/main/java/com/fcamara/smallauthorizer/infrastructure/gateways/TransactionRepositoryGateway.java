package com.fcamara.smallauthorizer.infrastructure.gateways;

import com.fcamara.smallauthorizer.application.gateways.TransactionGateway;
import com.fcamara.smallauthorizer.domain.TransactionDomain;
import com.fcamara.smallauthorizer.infrastructure.persistence.entity.TransactionEntity;
import com.fcamara.smallauthorizer.infrastructure.persistence.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionRepositoryGateway implements TransactionGateway {

    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public TransactionDomain save(TransactionDomain transactionDomain) {
        var transactionSaved = transactionRepository.save(modelMapper.map(transactionDomain, TransactionEntity.class));
        return modelMapper.map(transactionSaved, TransactionDomain.class);
    }

}
