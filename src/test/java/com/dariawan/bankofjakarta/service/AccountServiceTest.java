/**
 * Bank of Jakarta V1 (https://www.dariawan.com)
 * Copyright (C) 2018 Dariawan <hello@dariawan.com>
 *
 * PLEASE NOTE: Your use of this software is subject to the terms and conditions of the license agreement by which you acquired this software.
 * You may not use this software if you have not validly acquired a license for the software from Dariawan or its licensed distributors.
 */
package com.dariawan.bankofjakarta.service;

import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.exception.AccountNotFoundException;
import com.dariawan.bankofjakarta.exception.CustomerNotFoundException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * abstract class AccountServiceTest 
 */
public abstract class AccountServiceTest extends BaseServiceTest {
    private final BigDecimal bigZero = new BigDecimal("0.00");
    public abstract AccountService getAccountService();
    
    @Test
    public void testGetDetails() throws InvalidParameterException, AccountNotFoundException {
        Account acc = getAccountService().getDetails("5008");
        verifyAccount(acc);
    }
    
    @Test
    public void testCreateAccount() throws CustomerNotFoundException, InvalidParameterException {
        Account account = new Account();
        account.setType("Savings");
        account.setDescription("Savings Account");
        account.setBalance(bigZero);
        account.setCreditLine(bigZero);
        account.setBeginBalance(bigZero);
        account.setBeginBalanceTimeStamp(new GregorianCalendar(118, 12, 24, 11, 10, 10).getTime());
        
        getAccountService().createAccount(account, "200");
    }
    
    @Test
    public void testRemoveAccount() throws InvalidParameterException, AccountNotFoundException {
        Account acc1 = getAccountService().getDetails("5005");
        verifyAccount(acc1);
        
        getAccountService().removeAccount("5005");
        
        try {
            getAccountService().getDetails("5005");
        }
        catch (AccountNotFoundException ex) {
            // AccountNotFoundException thrown since 5005 not exists anymore
        }
    }
         
    @Test
    public void testAddCustomerToAccount() throws InvalidParameterException, CustomerNotFoundException, AccountNotFoundException {
        getAccountService().addCustomerToAccount("200", "5005");
    }

    @Test
    public void testRemoveCustomerFromAccount() throws InvalidParameterException, CustomerNotFoundException, AccountNotFoundException {
        getAccountService().removeCustomerFromAccount("200", "5005");
    }
    
    @Test
    public void testGetAccountsOfCustomer() throws InvalidParameterException, CustomerNotFoundException {
        List<Account> accounts = getAccountService().getAccountsOfCustomer("200");
        Assert.assertNotNull(accounts);
        Assert.assertFalse(accounts.isEmpty());
    }

    @Test
    public void testGetCustomerIds() throws InvalidParameterException, AccountNotFoundException {
        List<String> customerIds = getAccountService().getCustomerIds("5005");
        Assert.assertNotNull(customerIds);
        Assert.assertFalse(customerIds.isEmpty());
    }    
}
