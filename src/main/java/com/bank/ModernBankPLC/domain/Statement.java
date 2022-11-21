package com.bank.ModernBankPLC.domain;

import com.bank.ModernBankPLC.entity.ActivityJpaEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
public class Statement {

    @JsonProperty(value = "account-id")
    @Getter
    private final Long account;

    @Getter
    private final Double amount;

    @Getter
    private final Account.Currency currency;

    @Getter
    private final ActivityJpaEntity.TransferType type;

    @Getter
    private final LocalDateTime transactionDate;
}
