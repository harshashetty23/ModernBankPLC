package com.bank.ModernBankPLC.mapper;

import com.bank.ModernBankPLC.domain.Statement;
import com.bank.ModernBankPLC.entity.ActivityJpaEntity;

public class ActivityMapper {

    public static Statement mapToStatement(ActivityJpaEntity activityJpaEntity) {
        return Statement.builder().account(activityJpaEntity.getOwnerAccountId())
                .currency(activityJpaEntity.getCurrency())
                .amount(activityJpaEntity.getAmount())
                .type(activityJpaEntity.getTransferType())
                .transactionDate(activityJpaEntity.getTimestamp())
                .build();

    }
}
