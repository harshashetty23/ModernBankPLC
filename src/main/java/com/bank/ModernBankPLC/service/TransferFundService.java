package com.bank.ModernBankPLC.service;

import com.bank.ModernBankPLC.domain.Account;
import com.bank.ModernBankPLC.domain.TransferMoney;
import com.bank.ModernBankPLC.exception.InsufficientFundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.UUID;

import static com.bank.ModernBankPLC.entity.ActivityJpaEntity.TransferType.CREDIT;
import static com.bank.ModernBankPLC.entity.ActivityJpaEntity.TransferType.DEBIT;

@RequiredArgsConstructor
@Component
@Transactional
public class TransferFundService {

    private final AccountService accountService;
    private final ActivityService activityService;

    public void transferFund(TransferMoney command) {

        final Account sourceAccount = accountService.loadAccountWithLock(command.getSourceAccountId());

        if (!sourceAccount.mayWithdraw(command.getMoney())) {
            throw new InsufficientFundException("Source account doesnt have enough fund : " + sourceAccount.getId());
        }

        final Account targetAccount = accountService.loadAccountWithLock(command.getTargetAccountId());

        final Account updatedSourceAccount = sourceAccount.withDraw(command.getMoney());
        final Account depositedtargetAccount = targetAccount.deposit(command.getMoney());

        accountService.updateAccountFund(updatedSourceAccount);
        accountService.updateAccountFund(depositedtargetAccount);


        String correltationId = UUID.randomUUID().toString();
        activityService.storeActivity(updatedSourceAccount, updatedSourceAccount, depositedtargetAccount, CREDIT, correltationId, command.getMoney());
        activityService.storeActivity(depositedtargetAccount, updatedSourceAccount, depositedtargetAccount, DEBIT, correltationId, command.getMoney());

    }
}
