package com.bank.ModernBankPLC.repository;

import com.bank.ModernBankPLC.entity.AccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountJpaEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Optional<AccountJpaEntity> findById(Long customerId);

    @Query("select u from  AccountJpaEntity u where u.id = ?1")
    public Optional<AccountJpaEntity> findByIdWithoutLock(Long customerId);

    @Modifying
    @Query("update AccountJpaEntity u set u.amount = ?2 where u.id = ?1")
    void updateAccountFund(Long id, double amount);
}
