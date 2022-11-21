package com.bank.ModernBankPLC.service;

import com.bank.ModernBankPLC.domain.Account;
import com.bank.ModernBankPLC.domain.Money;
import com.bank.ModernBankPLC.entity.AccountJpaEntity;
import com.bank.ModernBankPLC.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.bank.ModernBankPLC.domain.Account.Currency.GBP;
import static com.bank.ModernBankPLC.helper.AccountHelper.getMockAccountJpaEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountService accountService;

    @Test
    void loadAccountWithLockOK(){
        Long accountId = 1L;
        AccountJpaEntity accountJpaEntity = getMockAccountJpaEntity(accountId);
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(accountJpaEntity));

        Account account = accountService.loadAccountWithLock(new Account.AccountId(accountId));

        assertEquals(accountJpaEntity.getAmount(),account.getBaselineBalance().getAmount().doubleValue());
        assertEquals(accountJpaEntity.getCurrency(),account.getCurrency());
    }

}
