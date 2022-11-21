package com.bank.ModernBankPLC.service;

import com.bank.ModernBankPLC.domain.Account;
import com.bank.ModernBankPLC.domain.Money;
import com.bank.ModernBankPLC.domain.Statement;
import com.bank.ModernBankPLC.entity.AccountJpaEntity;
import com.bank.ModernBankPLC.entity.ActivityJpaEntity;
import com.bank.ModernBankPLC.mapper.ActivityMapper;
import com.bank.ModernBankPLC.repository.AccountRepository;
import com.bank.ModernBankPLC.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ActivityService {

    private final ActivityRepository activityRepository;

    private final AccountRepository accountRepository;

    public void storeActivity(Account ownerAccount, Account updatedSourceAccount, Account depositedtargetAccount, ActivityJpaEntity.TransferType withdraw, String correltationId, Money money) {
        ActivityJpaEntity activity = new ActivityJpaEntity(null, correltationId, LocalDateTime.now(), ownerAccount.getId().getValue(), updatedSourceAccount.getId().getValue(), depositedtargetAccount.getId().getValue(), withdraw, money.getAmount().doubleValue(), ownerAccount.getCurrency());
        activityRepository.save(activity);
    }


    public List<Statement> fetchStatement(Long accountId) {
        AccountJpaEntity account =
                accountRepository.findById(accountId)
                        .orElseThrow(() -> new EntityNotFoundException("accountId not found: " + accountId));
        PageRequest page = PageRequest.of(0, 20);
        final List<ActivityJpaEntity> allByoOwnerAccountId = activityRepository.findAllByOwnerAccountId(accountId, page);

        return allByoOwnerAccountId.stream().map(ActivityMapper::mapToStatement).collect(Collectors.toList());

    }
}
