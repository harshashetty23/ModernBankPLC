package com.bank.ModernBankPLC.controller;

import com.bank.ModernBankPLC.exception.InsufficientFundException;
import com.bank.ModernBankPLC.exception.InvalidFundTransferOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<Object> handle(ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body("Validation Failed -->" + ex.getMessage());
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handle(EntityNotFoundException ex) {
        return ResponseEntity.badRequest().body("invalid account number -->" + ex.getMessage());
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handle(InsufficientFundException ex) {
        return ResponseEntity.badRequest().body("InSuffecient Fund -->" + ex.getMessage());
    }

    @ExceptionHandler
    protected ResponseEntity<Object> handle(InvalidFundTransferOperation ex) {
        return ResponseEntity.badRequest().body("Invalid Transfer Fund Operation -->" + ex.getMessage());
    }
}
