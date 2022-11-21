package com.bank.ModernBankPLC.repository;

import com.bank.ModernBankPLC.domain.Account;
import com.bank.ModernBankPLC.entity.ActivityJpaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static com.bank.ModernBankPLC.entity.ActivityJpaEntity.TransferType.DEBIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ActivityRepositoryTest {

    @Autowired
    ActivityRepository activityRepository;

    @Test
    void storeAndCheckRecordOk(){

        activityRepository.save(new ActivityJpaEntity(null,"", LocalDateTime.now(),1l,2l,1l,DEBIT,100.2, Account.Currency.GBP));

        final List<ActivityJpaEntity> allByOwnerAccountId = activityRepository.findAllByOwnerAccountId(1L, Pageable.ofSize(10));

        assertNotNull(allByOwnerAccountId);
        assertEquals(1,allByOwnerAccountId.size());


    }
}
