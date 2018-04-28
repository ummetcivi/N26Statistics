package com.ummetcivi.n26statistics.controller;

import com.ummetcivi.n26statistics.TestConstants;
import com.ummetcivi.n26statistics.TestUtils;
import com.ummetcivi.n26statistics.controller.dto.TransactionDto;
import com.ummetcivi.n26statistics.domain.Transaction;
import com.ummetcivi.n26statistics.service.TransactionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TransactionControllerTest {
    @InjectMocks
    private TransactionController underTest;

    @Mock
    private TransactionService transactionService;

    @Mock
    private ConfigurableConversionService configurableConversionService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnHttpOKWhenTimestampIsValid() {
        TransactionDto transactionDto = TestUtils.createTransactionDto(TestConstants.ANY_TIMESTAMP, TestConstants.ANY_AMOUNT);

        Transaction transaction = TestUtils.createTransaction(transactionDto);

        Mockito.when(configurableConversionService.convert(transactionDto, Transaction.class)).thenReturn(transaction);
        Mockito.when(transactionService.saveTransaction(transaction)).thenReturn(true);

        ResponseEntity<Void> result = underTest.saveTransaction(transactionDto);

        Mockito.verify(transactionService).saveTransaction(transaction);
        Assert.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    public void shouldReturnHttpNoContentWhenTimestampIsNotValid() {
        TransactionDto transactionDto = TestUtils.createTransactionDto(TestConstants.ANY_TIMESTAMP - 60 * 1000,
                TestConstants.ANY_AMOUNT);

        Transaction transaction = TestUtils.createTransaction(transactionDto);

        Mockito.when(configurableConversionService.convert(transactionDto, Transaction.class)).thenReturn(transaction);
        Mockito.when(transactionService.saveTransaction(transaction)).thenReturn(false);

        ResponseEntity<Void> result = underTest.saveTransaction(transactionDto);

        Mockito.verify(transactionService).saveTransaction(transaction);
        Assert.assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
}