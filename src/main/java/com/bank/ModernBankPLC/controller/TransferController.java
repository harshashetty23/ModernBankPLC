package com.bank.ModernBankPLC.controller;


import com.bank.ModernBankPLC.domain.Account.AccountId;
import com.bank.ModernBankPLC.domain.Money;
import com.bank.ModernBankPLC.domain.TransferMoney;
import com.bank.ModernBankPLC.service.TransferFundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransferController {

    private final TransferFundService transferFundService;

    @PostMapping(path = "/transfer/{sourceAccountId}/{targetAccountId}/{amount}")
    ResponseEntity<Void> transferFund(
            @PathVariable("sourceAccountId") Long sourceAccountId,
            @PathVariable("targetAccountId") Long targetAccountId,
            @PathVariable("amount") Double amount) {

        TransferMoney command = new TransferMoney(
                new AccountId(sourceAccountId),
                new AccountId(targetAccountId),
                Money.of(amount));

        transferFundService.transferFund(command);

        return ResponseEntity.ok().build();
    }
}
