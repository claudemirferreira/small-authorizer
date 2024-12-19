package com.fcamara.smallauthorizer.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDomain {

    private UUID id;
    private LocalDateTime lastModifiedAt;
    private BigDecimal value;
    private CardDomain cardDomain;

    public TransactionDomain(final CardDomain cardDomain, final BigDecimal value, final LocalDateTime time) {
        this.cardDomain = cardDomain;
        this.value = value;
        this.setLastModifiedAt(time);
    }

}
