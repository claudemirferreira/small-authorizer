package com.fcamara.smallauthorizer.infrastructure.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {

    @NotBlank
    @JsonProperty("numeroCartao")
    private String number;

    @NotBlank
    @JsonProperty("senhaCartao")
    private String password;

    @NotNull
    @JsonProperty("valor")
    private BigDecimal amount;

}
