package com.bank.ModernBankPLC.integration;

import com.bank.ModernBankPLC.domain.Account;
import com.bank.ModernBankPLC.domain.Money;
import com.bank.ModernBankPLC.domain.Statement;
import com.bank.ModernBankPLC.domain.TransferMoney;
import com.bank.ModernBankPLC.repository.AccountRepository;
import com.bank.ModernBankPLC.repository.ActivityRepository;
import com.bank.ModernBankPLC.service.ActivityService;
import com.bank.ModernBankPLC.service.TransferFundService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.bank.ModernBankPLC.domain.Account.Currency.GBP;
import static com.bank.ModernBankPLC.entity.ActivityJpaEntity.TransferType.CREDIT;
import static com.bank.ModernBankPLC.entity.ActivityJpaEntity.TransferType.DEBIT;
import static com.bank.ModernBankPLC.helper.AccountHelper.AMOUNT;
import static com.bank.ModernBankPLC.helper.AccountHelper.getMockAccountJpaEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MiniStatementGenerationTest {

    private static final Double SOURCE_AMOUNT = 2.1;
    @Autowired
    TransferFundService transferFundService;

    @Autowired
    ActivityService activityService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ActivityRepository activityRepository;


    @BeforeEach
    void cleanup()
    {
        activityRepository.deleteAll();
    }

    @Test
    void testMiniStatementGenerationPostFundTransfer() {

        //Given
        Long sourceAccountId = 1l;
        Long targetAccountId = 2l;
        accountRepository.save(getMockAccountJpaEntity(sourceAccountId));
        accountRepository.save(getMockAccountJpaEntity(targetAccountId));

        TransferMoney command = new TransferMoney(new Account.AccountId(sourceAccountId), new Account.AccountId(targetAccountId), Money.of(2.1),GBP);


        transferFundService.transferFund(command);


        //when
        List<Statement> sourceStatements = activityService.fetchStatement(sourceAccountId);
        List<Statement> targetStatements = activityService.fetchStatement(targetAccountId);


        //then
        assertEquals(sourceAccountId, sourceStatements.get(0).getAccount());
        assertEquals(GBP, sourceStatements.get(0).getCurrency());
        assertEquals(CREDIT, sourceStatements.get(0).getType());
        assertEquals(SOURCE_AMOUNT, sourceStatements.get(0).getAmount());

        assertEquals(targetAccountId, targetStatements.get(0).getAccount());
        assertEquals(GBP, targetStatements.get(0).getCurrency());
        assertEquals(DEBIT, targetStatements.get(0).getType());
        assertNotNull(targetStatements.get(0).getTransactionDate());
        assertNotNull(targetStatements.get(0).toString());
        assertNotNull(targetStatements.get(0).hashCode());
        assertTrue(targetStatements.get(0).equals(targetStatements.get(0)));
    }

}
