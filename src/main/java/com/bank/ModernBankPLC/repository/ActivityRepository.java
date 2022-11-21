package com.bank.ModernBankPLC.repository;


import com.bank.ModernBankPLC.entity.ActivityJpaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<ActivityJpaEntity, Long> {


    List<ActivityJpaEntity> findAllByOwnerAccountId(Long accountId, Pageable pageable);
}
