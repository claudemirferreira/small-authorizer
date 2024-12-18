package com.fcamara.smallauthorizer.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "card")
@Entity
public class CardEntity extends AbstractEntity {

    private String password;

    private String number;

    @Column(name = "balance", columnDefinition = "decimal", precision = 15, scale = 2)
    private BigDecimal balance;

    @OneToMany(mappedBy = "card")
    private List<TransactionEntity> transactions;

}
