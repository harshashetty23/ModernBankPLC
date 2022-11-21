package com.bank.ModernBankPLC.controller;

import com.bank.ModernBankPLC.domain.Balance;
import com.bank.ModernBankPLC.domain.Statement;
import com.bank.ModernBankPLC.service.AccountService;
import com.bank.ModernBankPLC.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final ActivityService activityService;

    @GetMapping(path = "/accounts/{accountId}/balance")
    public ResponseEntity<Balance> fetchBalance(@PathVariable("accountId") Long accountId) {

        Balance balance = accountService.fetchBalance(accountId);
        return ResponseEntity.ok(balance);
    }

    @GetMapping(path = "/accounts/{accountId}/statements/mini")
    public ResponseEntity<List<Statement>> fetchMiniStatements(@PathVariable("accountId") Long accountId) {

        List<Statement> statements = activityService.fetchStatement(accountId);
        return ResponseEntity.ok(statements);
    }

    @PostMapping(path = "/accounts/{currency}/{amount}/create")
    public ResponseEntity<Long> createNewAccount(@PathVariable("amount") Double amount, @PathVariable("currency") String currency) {

        return ResponseEntity.ok(accountService.createAccount(amount, currency));
    }
}
