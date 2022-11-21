package com.bank.ModernBankPLC.integration;

import com.bank.ModernBankPLC.domain.Account;
import com.bank.ModernBankPLC.domain.Money;
import com.bank.ModernBankPLC.domain.TransferMoney;
import com.bank.ModernBankPLC.entity.AccountJpaEntity;
import com.bank.ModernBankPLC.entity.ActivityJpaEntity;
import com.bank.ModernBankPLC.exception.InsufficientFundException;
import com.bank.ModernBankPLC.exception.InvalidFundTransferOperation;
import com.bank.ModernBankPLC.repository.AccountRepository;
import com.bank.ModernBankPLC.repository.ActivityRepository;
import com.bank.ModernBankPLC.service.TransferFundService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.bank.ModernBankPLC.domain.Account.Currency.EUR;
import static com.bank.ModernBankPLC.domain.Account.Currency.GBP;
import static com.bank.ModernBankPLC.entity.ActivityJpaEntity.TransferType.CREDIT;
import static com.bank.ModernBankPLC.entity.ActivityJpaEntity.TransferType.DEBIT;
import static com.bank.ModernBankPLC.helper.AccountHelper.getMockAccountJpaEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class TransferFundServiceTest {

    @Autowired
    TransferFundService transferFundService;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void cleanup()
    {
        activityRepository.deleteAll();
    }


    @Test
    void testFundTransferBetweenAccountAndCheckActivities() {

        //Given
        Long sourceAccountId = 1l;
        Long targetAccountId = 2l;
        accountRepository.save(getMockAccountJpaEntity(sourceAccountId));
        accountRepository.save(getMockAccountJpaEntity(targetAccountId));

        TransferMoney command = new TransferMoney(new Account.AccountId(sourceAccountId), new Account.AccountId(targetAccountId), Money.of(2.1),GBP);

        //when
        transferFundService.transferFund(command);


        //then
        final Optional<AccountJpaEntity> sourceAccountPostTransferFund = accountRepository.findByIdWithoutLock(sourceAccountId);
        final Optional<AccountJpaEntity> targetAccountPostTransferFund = accountRepository.findByIdWithoutLock(targetAccountId);

        assertEquals(8.1, sourceAccountPostTransferFund.get().getAmount());
        assertEquals(12.3, targetAccountPostTransferFund.get().getAmount());
        assertNotNull(targetAccountPostTransferFund.get().hashCode());
        assertNotNull(targetAccountPostTransferFund.get().toString());

        List<ActivityJpaEntity> activityJpaEntities = activityRepository.findAll();

        assertEquals(2, activityJpaEntities.size());

        List<ActivityJpaEntity> allBySourceAccountId = activityRepository.findAllByOwnerAccountId(sourceAccountId, Pageable.ofSize(10));

        assertEquals(sourceAccountId, allBySourceAccountId.get(0).getOwnerAccountId());
        assertEquals(GBP, allBySourceAccountId.get(0).getCurrency());
        assertEquals(CREDIT, allBySourceAccountId.get(0).getTransferType());

        List<ActivityJpaEntity> allBytargetAccountId = activityRepository.findAllByOwnerAccountId(targetAccountId, Pageable.ofSize(10));

        assertEquals(targetAccountId, allBytargetAccountId.get(0).getOwnerAccountId());
        assertEquals(sourceAccountId, allBytargetAccountId.get(0).getSourceAccountId());
        assertEquals(targetAccountId, allBytargetAccountId.get(0).getTargetAccountId());
        assertNotNull(allBytargetAccountId.get(0).getCorrelationId());
        assertTrue(allBytargetAccountId.get(0).equals(allBytargetAccountId.get(0)));
        assertEquals(GBP, allBytargetAccountId.get(0).getCurrency());
        assertEquals(DEBIT, allBytargetAccountId.get(0).getTransferType());
    }


    @Test
    void testFundTransferBetweenAccountAnsShouldThrowExceptionForInsufficientFund() {

        //Given
        Long sourceAccountId = 1l;
        Long targetAccountId = 2l;
        accountRepository.save(getMockAccountJpaEntity(sourceAccountId));
        accountRepository.save(getMockAccountJpaEntity(targetAccountId));

        TransferMoney command = new TransferMoney(new Account.AccountId(sourceAccountId), new Account.AccountId(targetAccountId), Money.of(112.1),GBP);

        //when
        try {
            transferFundService.transferFund(command);
            fail();
        } catch (Exception e) {

            assertTrue(e instanceof InsufficientFundException);
            assertTrue(e.getMessage().contains("Source account doesnt have enough fund : AccountId"));
        }

        assertNotNull(command.hashCode());
        assertNotNull(command.toString());
    }

    @Test
    void testFundTransferBetweenAccountAnsShouldThrowExceptionForInvalidCurrencyUsed() {

        //Given
        Long sourceAccountId = 1l;
        Long targetAccountId = 2l;
        accountRepository.save(getMockAccountJpaEntity(sourceAccountId));
        accountRepository.save(getMockAccountJpaEntity(targetAccountId));

        TransferMoney command = new TransferMoney(new Account.AccountId(sourceAccountId), new Account.AccountId(targetAccountId), Money.of(2.1),EUR);

        //when
        try {
            transferFundService.transferFund(command);
            fail();
        } catch (Exception e) {

            assertTrue(e instanceof InvalidFundTransferOperation);
            assertTrue(e.getMessage().contains("Source account Currency"));
        }
    }

}
