package com.bank.ModernBankPLC.domain;

import com.bank.ModernBankPLC.common.SelfValidating;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {

    public enum Currency {GBP}

    ;

    /**
     * The unique ID of the account.
     */
    @Getter
    private final AccountId id;

    /**
     * The baseline balance of the account. .
     */
    @Getter
    private final Money baselineBalance;

    @Getter
    private Currency currency;

    public static Account withId(
            AccountId accountId,
            Money baselineBalance,
            Currency currency) {
        return new Account(accountId, baselineBalance, currency);
    }

    public Account withDraw(Money money) {
        return new Account(this.id, baselineBalance.minus(money), this.currency);
    }

    public Account deposit(Money money) {
        return new Account(this.id, baselineBalance.plus(money), this.currency);
    }


    public boolean mayWithdraw(Money money) {
        return Money.add(this.baselineBalance,
                        money.negate())
                .isPositiveOrZero();
    }

    public static class AccountId extends SelfValidating<AccountId> {

        @NotNull
        @Getter
        private Long value;

        @Override
        public String toString() {
            return "AccountId=" + value;
        }

        public AccountId(Long value) {
            this.value = value;
            this.validateSelf();
        }
    }
}
