package com.fcamara.smallauthorizer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDomain {

    private UUID id;

    private LocalDateTime lastModifiedAt;

    private String password;

    private String number;

    private BigDecimal balance;

    private List<TransactionDomain> transactionDomains;

}
