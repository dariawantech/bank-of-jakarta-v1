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
import com.dariawan.bankofjakarta.exception.CustomerNotFoundException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import java.util.GregorianCalendar;
import org.junit.Test;

public class AccountServiceImplTest extends BaseServiceImplTest {

    @Test(expected=InvalidParameterException.class)
    public void testGetDetailsInvalidParameterException() throws InvalidParameterException, AccountNotFoundException {
        accountService.getDetails("");
    }

    @Test(expected=AccountNotFoundException.class)
    public void testGetDetailsAccountNotFoundException() throws InvalidParameterException, AccountNotFoundException {
        accountService.getDetails("5010");
    }
    
    @Test(expected=InvalidParameterException.class)
    public void testCreateAccountEmptyType() throws CustomerNotFoundException, InvalidParameterException {
        Account account = new Account();
        account.setType("");
        account.setDescription("Savings Account");
        account.setBalance(bigZero);
        account.setCreditLine(bigZero);
        account.setBeginBalance(bigZero);
        account.setBeginBalanceTimeStamp(new GregorianCalendar(118, 12, 24, 11, 10, 10).getTime());

        accountService.createAccount(account, "200");
    }

    @Test(expected=InvalidParameterException.class)
    public void testCreateAccountEmptyDescription() throws CustomerNotFoundException, InvalidParameterException {
        Account account = new Account();
        account.setType("Savings");
        account.setDescription("");
        account.setBalance(bigZero);
        account.setCreditLine(bigZero);
        account.setBeginBalance(bigZero);
        account.setBeginBalanceTimeStamp(new GregorianCalendar(118, 12, 24, 11, 10, 10).getTime());

        accountService.createAccount(account, "200");
    }
    
    @Test(expected=InvalidParameterException.class)
    public void testCreateAccountNullBeginBalanceTimeStamp() throws CustomerNotFoundException, InvalidParameterException {
        Account account = new Account();
        account.setType("Savings");
        account.setDescription("Savings Account");
        account.setBalance(bigZero);
        account.setCreditLine(bigZero);
        account.setBeginBalance(bigZero);
        account.setBeginBalanceTimeStamp(null);

        accountService.createAccount(account, "200");
    }
    
    @Test(expected=InvalidParameterException.class)
    public void testCreateAccountNullCustomerId() throws CustomerNotFoundException, InvalidParameterException {
        Account account = new Account();
        account.setType("Savings");
        account.setDescription("Savings Account");
        account.setBalance(bigZero);
        account.setCreditLine(bigZero);
        account.setBeginBalance(bigZero);
        account.setBeginBalanceTimeStamp(new GregorianCalendar(118, 12, 24, 11, 10, 10).getTime());

        accountService.createAccount(account, null);
    }
    
    @Test(expected=CustomerNotFoundException.class)
    public void testCreateAccountCustomerNotFoundException() throws CustomerNotFoundException, InvalidParameterException {
        Account account = new Account();
        account.setType("Savings");
        account.setDescription("Savings Account");
        account.setBalance(bigZero);
        account.setCreditLine(bigZero);
        account.setBeginBalance(bigZero);
        account.setBeginBalanceTimeStamp(new GregorianCalendar(118, 12, 24, 11, 10, 10).getTime());

        accountService.createAccount(account, "5010");
    }
    
    @Test(expected=InvalidParameterException.class)
    public void testRemoveAccountInvalidParameterException() throws InvalidParameterException, AccountNotFoundException {
        accountService.removeAccount("");
    }
    
    @Test(expected=AccountNotFoundException.class)
    public void testRemoveAccountAccountNotFoundException() throws InvalidParameterException, AccountNotFoundException {
        accountService.removeAccount("5010");
    }

    @Test(expected=InvalidParameterException.class)
    public void testAddCustomerToAccountEmptyCustomerId() throws InvalidParameterException, CustomerNotFoundException, AccountNotFoundException {
        accountService.addCustomerToAccount("", "5005");
    }

    @Test(expected=InvalidParameterException.class)
    public void testAddCustomerToAccountEmptyAccountId() throws InvalidParameterException, CustomerNotFoundException, AccountNotFoundException {
        accountService.addCustomerToAccount("200", null);
    }
    
    @Test(expected=AccountNotFoundException.class)
    public void testAddCustomerToAccountAccountNotFoundException() throws InvalidParameterException, CustomerNotFoundException, AccountNotFoundException {
        accountService.addCustomerToAccount("200", "5010");
    }
    
    @Test(expected=CustomerNotFoundException.class)
    public void testAddCustomerToAccountCustomerNotFoundException() throws InvalidParameterException, CustomerNotFoundException, AccountNotFoundException {
        accountService.addCustomerToAccount("210", "5005");
    }
    
    @Test(expected=InvalidParameterException.class)
    public void testRemoveCustomerFromAccountEmptyCustomerId() throws InvalidParameterException, CustomerNotFoundException, AccountNotFoundException {
        accountService.removeCustomerFromAccount("", "5005");
    }

    @Test(expected=InvalidParameterException.class)
    public void testRemoveCustomerFromAccountEmptyAccountId() throws InvalidParameterException, CustomerNotFoundException, AccountNotFoundException {
        accountService.removeCustomerFromAccount("200", null);
    }
    
    @Test(expected=CustomerNotFoundException.class)
    public void testRemoveCustomerFromAccountAccountNotFoundException() throws InvalidParameterException, CustomerNotFoundException, AccountNotFoundException {
        accountService.removeCustomerFromAccount("210", "5005");
    }
    
    @Test(expected=AccountNotFoundException.class)
    public void testRemoveCustomerFromAccountCustomerNotFoundException() throws InvalidParameterException, CustomerNotFoundException, AccountNotFoundException {
        accountService.removeCustomerFromAccount("200", "5010");
    }
    
    @Test(expected=InvalidParameterException.class)
    public void testGetAccountsOfCustomerInvalidParameterException() throws InvalidParameterException, CustomerNotFoundException {
        accountService.getAccountsOfCustomer(null);
    }
     
    @Test(expected=CustomerNotFoundException.class)
    public void testGetAccountsOfCustomerCustomerNotFoundException() throws InvalidParameterException, CustomerNotFoundException {
        accountService.getAccountsOfCustomer("210");
    }
    
    @Test(expected=InvalidParameterException.class)
    public void testGetCustomerIdsInvalidParameterException() throws InvalidParameterException, AccountNotFoundException {
        accountService.getCustomerIds("");
    }
    
    @Test(expected=AccountNotFoundException.class)
    public void testGetCustomerIdsAccountNotFoundException() throws InvalidParameterException, AccountNotFoundException {
        accountService.getCustomerIds("210");
    }
}
