package com.bank.ModernBankPLC.entity;

import com.bank.ModernBankPLC.domain.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "activity")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityJpaEntity {

    public enum TransferType {DEBIT, CREDIT}

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String correlationId;

    @Column
    private LocalDateTime timestamp;

    @Column
    private Long ownerAccountId;

    @Column
    private Long sourceAccountId;

    @Column
    private Long targetAccountId;

    @Enumerated
    @Column
    private TransferType transferType;

    @Column
    private Double amount;

    private Account.Currency currency;

}
