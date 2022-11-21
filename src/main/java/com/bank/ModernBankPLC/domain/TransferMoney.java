package com.bank.ModernBankPLC.domain;

import lombok.Getter;


public class TransferMoney {


    @Getter
    private final Account.AccountId sourceAccountId;

    @Getter
    private final Account.AccountId targetAccountId;

    @Getter
    private final Money money;

    @Getter
    private final Account.Currency currency;

    public TransferMoney(Account.AccountId sourceAccountId, Account.AccountId targetAccountId, Money money, Account.Currency currency) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        this.currency = currency;
    }
}
