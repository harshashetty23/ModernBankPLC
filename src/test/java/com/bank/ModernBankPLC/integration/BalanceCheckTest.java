package com.bank.ModernBankPLC.integration;

import com.bank.ModernBankPLC.domain.Balance;
import com.bank.ModernBankPLC.repository.AccountRepository;
import com.bank.ModernBankPLC.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.bank.ModernBankPLC.domain.Account.Currency.GBP;
import static com.bank.ModernBankPLC.helper.AccountHelper.AMOUNT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BalanceCheckTest {

    @Autowired
    AccountService accountService;


    @Autowired
    AccountRepository accountRepository;


    @Test
    void testFundTransferBetweenAccountAndCheckActivities() {

        //Given
        final Long accountId = accountService.createAccount(AMOUNT, GBP.name());

        //when
        final Balance balance = accountService.fetchBalance(accountId);

        //FINAL
        assertEquals(GBP,balance.getCurrency());
        assertEquals(AMOUNT,balance.getAmount());
        assertEquals(accountId,balance.getAccount());
        assertNotNull(balance.hashCode());
        assertNotNull(balance.toString());
        assertNotNull(balance.equals(balance));

    }
}
