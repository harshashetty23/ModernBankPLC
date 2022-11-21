package com.bank.ModernBankPLC.domain;

import com.bank.ModernBankPLC.common.SelfValidating;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
public class Money extends SelfValidating<Money> {

    @NotNull
    private final BigDecimal amount;

    public Money(BigDecimal amount) {
        this.amount = amount;
        this.validateSelf();
    }

    public static Money of(double value) {
        return new Money(BigDecimal.valueOf(value));
    }

    public Money negate() {
        return new Money(this.amount.negate());
    }

    public static Money add(Money a, Money b) {
        return new Money(a.amount.add(b.amount));
    }

    public boolean isPositiveOrZero() {
        return this.amount.compareTo(BigDecimal.ZERO) >= 0;
    }

    public Money minus(Money money) {
        return new Money(this.amount.subtract(money.amount));
    }

    public Money plus(Money money) {
        return new Money(this.amount.add(money.amount));
    }

}
