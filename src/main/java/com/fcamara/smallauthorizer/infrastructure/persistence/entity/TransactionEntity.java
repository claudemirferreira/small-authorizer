package com.fcamara.smallauthorizer.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TransactionEntity extends AbstractEntity {

    @Column(name="value",  columnDefinition="decimal", precision=15, scale=2)
    private BigDecimal value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_card")
    private CardEntity card;

}
