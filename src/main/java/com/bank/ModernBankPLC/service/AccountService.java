package com.bank.ModernBankPLC.service;

import com.bank.ModernBankPLC.domain.Account;
import com.bank.ModernBankPLC.domain.Account.AccountId;
import com.bank.ModernBankPLC.domain.Balance;
import com.bank.ModernBankPLC.domain.Money;
import com.bank.ModernBankPLC.entity.AccountJpaEntity;
import com.bank.ModernBankPLC.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Component
public class AccountService {

    private final AccountRepository accountRepository;

    public Account loadAccountWithLock(AccountId accountId) {
        AccountJpaEntity account =
                accountRepository.findById(accountId.getValue())
                        .orElseThrow(() -> new EntityNotFoundException("accountId not found: " + accountId));

        return Account.withId(new AccountId(account.getId()), Money.of(account.getAmount()), account.getCurrency());
    }

    public void updateAccountFund(Account updatedSourceAccount) {
        accountRepository.updateAccountFund(updatedSourceAccount.getId().getValue(), updatedSourceAccount.getBaselineBalance().getAmount().doubleValue());
    }

    public Balance fetchBalance(Long accountId) {
        AccountJpaEntity account =
                accountRepository.findByIdWithoutLock(accountId)
                        .orElseThrow(() -> new EntityNotFoundException("accountId not found: " + accountId));

        return new Balance(accountId.longValue(), account.getAmount(), account.getCurrency());
    }

    public Long createAccount(Double amount, String currency) {
        AccountJpaEntity accountJpaEntity = accountRepository.save(new AccountJpaEntity(null, amount, Account.Currency.valueOf(currency)));
        return accountJpaEntity.getId();
    }
}




