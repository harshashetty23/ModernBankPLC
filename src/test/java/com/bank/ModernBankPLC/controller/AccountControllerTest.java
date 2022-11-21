package com.bank.ModernBankPLC.controller;

import com.bank.ModernBankPLC.domain.Account;
import com.bank.ModernBankPLC.domain.Balance;
import com.bank.ModernBankPLC.domain.Statement;
import com.bank.ModernBankPLC.service.AccountService;
import com.bank.ModernBankPLC.service.ActivityService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static com.bank.ModernBankPLC.domain.Account.Currency.GBP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountControllerTest {

    private final AccountService accountService = mock(AccountService.class);
    private final ActivityService activityService = mock(ActivityService.class);

    private final AccountController accountController= new AccountController(accountService,activityService);

    @Test
    void testfetchMiniStatementsOk(){
        //Given
        Long accountId = 10L;
        List<Statement> statementList = new ArrayList<>();
        Statement statement =  Statement.builder().account(accountId).amount(10.1).currency(GBP).build();
        statementList.add(statement);
        when(activityService.fetchStatement(accountId)).thenReturn(statementList);

        //when
        ResponseEntity<List<Statement>> listResponseEntity = accountController.fetchMiniStatements(accountId);

        //then
        assertEquals(HttpStatus.OK, listResponseEntity.getStatusCode());
        assertEquals(200, listResponseEntity.getStatusCode().value());

        assertEquals(1,listResponseEntity.getBody().size());
    }

    @Test
    void testAccountBalanceCheckOk(){

        Long accountId = 10L;
        Balance balance = new Balance(accountId,10.2, GBP);
        when(accountService.fetchBalance(accountId)).thenReturn(balance);

        ResponseEntity<Balance> balanceResponseEntity = accountController.fetchBalance(accountId);

        assertEquals(HttpStatus.OK, balanceResponseEntity.getStatusCode());
        assertEquals(200, balanceResponseEntity.getStatusCode().value());

        assertEquals(10.2,balanceResponseEntity.getBody().getAmount());

    }

    @Test
    void testAccountCreationOk(){

        Long accountId = 10L;

        Double amount = 10.2;

        when(accountService.createAccount(amount, GBP.name())).thenReturn(accountId);

        final ResponseEntity<Long> newAccountResponseEntity = accountController.createNewAccount(amount, GBP.name());

        assertEquals(HttpStatus.OK, newAccountResponseEntity.getStatusCode());
        assertEquals(200, newAccountResponseEntity.getStatusCode().value());

        assertEquals(accountId,newAccountResponseEntity.getBody().longValue());

    }
}
