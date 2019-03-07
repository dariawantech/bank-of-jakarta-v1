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
