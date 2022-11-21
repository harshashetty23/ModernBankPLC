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


@Entity
@Table(name = "account")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountJpaEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Double amount;

    @Enumerated
    @Column
    private Account.Currency currency;
}
