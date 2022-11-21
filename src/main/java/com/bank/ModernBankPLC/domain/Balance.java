package com.bank.ModernBankPLC.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Balance {

    @JsonProperty(value = "account-id")
    @Getter
    private final Long account;

    @Getter
    private final Double amount;

    @Getter
    private final Account.Currency currency;

}
