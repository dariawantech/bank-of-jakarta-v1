/**
 * Bank of Jakarta V1 (https://www.dariawan.com)
 * Copyright (C) 2018 Dariawan <hello@dariawan.com>
 *
 * PLEASE NOTE: Your use of this software is subject to the terms and conditions of the license agreement by which you acquired this software.
 * You may not use this software if you have not validly acquired a license for the software from Dariawan or its licensed distributors.
 */
package com.dariawan.bankofjakarta.service.impl;

import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.exception.AccountNotFoundException;
import com.dariawan.bankofjakarta.exception.InsufficientCreditException;
import com.dariawan.bankofjakarta.exception.InsufficientFundsException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import com.dariawan.bankofjakarta.service.AccountService;
import com.dariawan.bankofjakarta.service.TxService;
import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TxServiceTest {
    
    protected TxService txService;
    protected AccountService accountService;

    @Before
    public void setUp() {
        // Create the application from the configuration
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "com/dariawan/bankofjakarta/**/spring-config-test.xml");
        // Look up the application service interface
        txService = context.getBean("txService", TxService.class);
        accountService = context.getBean("accountService", AccountService.class);
    }
    
    private Account getAccount(String accountId) {
        try {
            return accountService.getDetails(accountId);
        } catch (InvalidParameterException ex) {
            return null;
        } catch (AccountNotFoundException ex) {
            return null;
        }
    }
    
    @Test
    public void testTransferFunds() throws InvalidParameterException, AccountNotFoundException, InsufficientCreditException, InsufficientFundsException {
        Account accFrom1 = getAccount("5008");
        BigDecimal balanceFromNow = accFrom1.getBalance();
        
        Account accTo1 = getAccount("5007");
        BigDecimal balanceToNow = accTo1.getBalance();
        
        txService.transferFunds(new BigDecimal("200"), "Transfer 200", accFrom1.getAccountId(), accTo1.getAccountId());
        
        Account accFrom2 = getAccount("5008");
        assertEquals(accFrom2.getBalance().toString(), (balanceFromNow.subtract(new BigDecimal("200"))).toString());
        
        Account accTo2 = getAccount("5007");
        // because credit account - subtract
        assertEquals(accTo2.getBalance().toString(), (balanceToNow.subtract(new BigDecimal("200"))).toString());
    }
}
