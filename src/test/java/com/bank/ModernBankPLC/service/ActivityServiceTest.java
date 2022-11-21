package com.bank.ModernBankPLC.service;

import com.bank.ModernBankPLC.domain.Account;
import com.bank.ModernBankPLC.domain.Money;
import com.bank.ModernBankPLC.domain.Statement;
import com.bank.ModernBankPLC.entity.AccountJpaEntity;
import com.bank.ModernBankPLC.entity.ActivityJpaEntity;
import com.bank.ModernBankPLC.repository.AccountRepository;
import com.bank.ModernBankPLC.repository.ActivityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.bank.ModernBankPLC.domain.Account.Currency.GBP;
import static com.bank.ModernBankPLC.entity.ActivityJpaEntity.TransferType.DEBIT;
import static com.bank.ModernBankPLC.helper.AccountHelper.ACCOUNT_ID;
import static com.bank.ModernBankPLC.helper.AccountHelper.CORRELATION_ID;
import static com.bank.ModernBankPLC.helper.AccountHelper.getActivityList;
import static com.bank.ModernBankPLC.helper.AccountHelper.getMockAccount;
import static com.bank.ModernBankPLC.helper.AccountHelper.getMockAccountJpaEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceTest {

    @Mock
    AccountRepository accountRepository;

    @Mock
    ActivityRepository activityRepository;

    @InjectMocks
    ActivityService activityService;


    @Captor
    ArgumentCaptor<ActivityJpaEntity> activityJpaEntityArgumentCaptor;


    @Test
    void testActivityServiceEntitySaveOk() {


        final Account mockAccount = getMockAccount();
        Money money = Money.of(1L);
        activityService.storeActivity(mockAccount, mockAccount, mockAccount, DEBIT, CORRELATION_ID, money);

        verify(activityRepository, times(1)).save(activityJpaEntityArgumentCaptor.capture());

        ActivityJpaEntity capturedValue = activityJpaEntityArgumentCaptor.getValue();

        assertEquals(1L, capturedValue.getAmount());
        assertEquals(mockAccount.getCurrency(), capturedValue.getCurrency());
        assertEquals(DEBIT, capturedValue.getTransferType());
        assertEquals(CORRELATION_ID, capturedValue.getCorrelationId());
        assertNotNull(money.hashCode());
        assertNotNull(money.toString());
    }


    @Test
    void testMiniStatementFetchForAccountOk() {

        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(getMockAccountJpaEntity(ACCOUNT_ID)));
        when(activityRepository.findAllByOwnerAccountId(eq(ACCOUNT_ID), any())).thenReturn(getActivityList());

        List<Statement> statements = activityService.fetchStatement(ACCOUNT_ID);

        assertEquals(ACCOUNT_ID,statements.get(0).getAccount());
        assertEquals(GBP,statements.get(0).getCurrency());
    }

}
