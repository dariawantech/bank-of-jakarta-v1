/**
 * Bank of Jakarta v1 (https://www.dariawan.com)
 * Copyright (C) 2018 Dariawan <hello@dariawan.com>
 *
 * Creative Commons Attribution-ShareAlike 4.0 International License
 *
 * Under this license, you are free to:
 * # Share - copy and redistribute the material in any medium or format
 * # Adapt - remix, transform, and build upon the material for any purpose,
 *   even commercially.
 *
 * The licensor cannot revoke these freedoms
 * as long as you follow the license terms.
 *
 * License terms:
 * # Attribution - You must give appropriate credit, provide a link to the
 *   license, and indicate if changes were made. You may do so in any
 *   reasonable manner, but not in any way that suggests the licensor
 *   endorses you or your use.
 * # ShareAlike - If you remix, transform, or build upon the material, you must
 *   distribute your contributions under the same license as the original.
 * # No additional restrictions - You may not apply legal terms or
 *   technological measures that legally restrict others from doing anything the
 *   license permits.
 *
 * Notices:
 * # You do not have to comply with the license for elements of the material in
 *   the public domain or where your use is permitted by an applicable exception
 *   or limitation.
 * # No warranties are given. The license may not give you all of
 *   the permissions necessary for your intended use. For example, other rights
 *   such as publicity, privacy, or moral rights may limit how you use
 *   the material.
 *
 * You may obtain a copy of the License at
 *   https://creativecommons.org/licenses/by-sa/4.0/
 *   https://creativecommons.org/licenses/by-sa/4.0/legalcode
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
