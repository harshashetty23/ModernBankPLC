package com.bank.ModernBankPLC.exception;

public class InsufficientFundException extends RuntimeException {
    public InsufficientFundException(String s) {
        super(s);
    }
}
