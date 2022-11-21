package com.bank.ModernBankPLC.repository;

import com.bank.ModernBankPLC.entity.AccountJpaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.bank.ModernBankPLC.domain.Account.Currency.GBP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void storeAndFetchRecord() {
        AccountJpaEntity savedAccountJpaEntity = accountRepository.save(new AccountJpaEntity(null, 10.2, GBP));

        Optional<AccountJpaEntity> accountJpaEntity = accountRepository.findByIdWithoutLock(savedAccountJpaEntity.getId());

        assertTrue(accountJpaEntity.isPresent());
        assertEquals(accountJpaEntity.get().getId(),savedAccountJpaEntity.getId());
    }


    @Test
    void storeAndUpdateRecord() {
        AccountJpaEntity savedAccountJpaEntity = accountRepository.save(new AccountJpaEntity(null, 10.2, GBP));

        double newAmount = 30.10;
        accountRepository.updateAccountFund(savedAccountJpaEntity.getId(),newAmount);


        Optional<AccountJpaEntity> accountJpaEntity = accountRepository.findByIdWithoutLock(savedAccountJpaEntity.getId());

        assertTrue(accountJpaEntity.isPresent());
    }
}
