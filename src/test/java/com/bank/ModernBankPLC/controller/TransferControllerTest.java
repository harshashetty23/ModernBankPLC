package com.bank.ModernBankPLC.controller;

import com.bank.ModernBankPLC.service.TransferFundService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class TransferControllerTest {

    private final TransferFundService transferFundService = mock(TransferFundService.class);

    private TransferController transferController = new TransferController(transferFundService);

    @Test
    void testSendMoneyServiceOk(){

        final ResponseEntity<Void> voidResponseEntity = transferController.transferFund(10L, 11L, 20.0);

        assertEquals(HttpStatus.OK, voidResponseEntity.getStatusCode());
        assertEquals(200, voidResponseEntity.getStatusCode().value());

    }


    @Test
    void testTransferFundWithSourceAccountIdNull(){

        try {
            final ResponseEntity<Void> voidResponseEntity = transferController.transferFund(null, 11L, 20.0);
            fail();
        }
        catch (Exception e) {
            assertTrue(e instanceof ConstraintViolationException);
        }

    }
}
