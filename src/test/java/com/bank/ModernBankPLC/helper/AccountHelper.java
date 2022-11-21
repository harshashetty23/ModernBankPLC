package com.bank.ModernBankPLC.helper;

import com.bank.ModernBankPLC.domain.Account;
import com.bank.ModernBankPLC.domain.Money;
import com.bank.ModernBankPLC.entity.AccountJpaEntity;
import com.bank.ModernBankPLC.entity.ActivityJpaEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.bank.ModernBankPLC.domain.Account.Currency.GBP;
import static com.bank.ModernBankPLC.entity.ActivityJpaEntity.TransferType.DEBIT;

public class AccountHelper {

    public static final String CORRELATION_ID = "correlationId";
    public static final Long ACCOUNT_ID = 1L;
    public static final Double AMOUNT = 10.2;

    public static  List<ActivityJpaEntity> getActivityList() {
        ActivityJpaEntity activityJpaEntity = new ActivityJpaEntity(null,CORRELATION_ID, LocalDateTime.now(),ACCOUNT_ID,ACCOUNT_ID,ACCOUNT_ID,DEBIT,1.9,GBP);
        List<ActivityJpaEntity> activityJpaEntities = new ArrayList<>();
        activityJpaEntities.add(activityJpaEntity);
        return activityJpaEntities;
    }

    public static Account getMockAccount() {
        return Account.withId(new Account.AccountId(ACCOUNT_ID), Money.of(12.1), GBP);
    }

    public static AccountJpaEntity getMockAccountJpaEntity(Long accountId) {
        return new AccountJpaEntity(accountId, AMOUNT, GBP);
    }
}
