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
import com.dariawan.bankofjakarta.exception.IllegalAccountTypeException;
import com.dariawan.bankofjakarta.exception.InsufficientCreditException;
import com.dariawan.bankofjakarta.exception.InsufficientFundsException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 * abstract class TxServiceTest
 */
public abstract class TxServiceTest extends BaseServiceTest {

    public abstract TxService getTxService();
    public abstract AccountService getAccountService();
    
    private Account getAccount(String accountId) {
        try {
            Account acc = getAccountService().getDetails(accountId);
            verifyAccount(acc);
            
            return acc;
        } catch (InvalidParameterException ex) {
            return null;
        } catch (AccountNotFoundException ex) {
            return null;
        }
    }
    
    @Test
    public void testDeposit() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException {
        // as long as not credit account
        Account acc1 = getAccount("5008");
        BigDecimal balanceNow = acc1.getBalance();
        getTxService().deposit(new BigDecimal("100"), "Deposit 100", acc1.getAccountId());
        Account acc2 = getAccount("5008");
        assertEquals(acc2.getBalance().toString(), (balanceNow.add(new BigDecimal("100"))).toString());
    }
    
    @Test
    public void testWithdraw() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException, InsufficientFundsException {
        // as long as not credit account
        Account acc1 = getAccount("5008");
        BigDecimal balanceNow = acc1.getBalance();
        getTxService().withdraw(new BigDecimal("230"), "Withdraw 230", acc1.getAccountId());
        Account acc2 = getAccount("5008");
        assertEquals(acc2.getBalance().toString(), (balanceNow.subtract(new BigDecimal("230"))).toString());
    }
    
    @Test
    public void testMakeCharge() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException, InsufficientCreditException {
        // as long as credit account
        Account acc1 = getAccount("5007");
        BigDecimal balanceNow = acc1.getBalance();
        getTxService().makeCharge(new BigDecimal("1500"), "Charge 1500", acc1.getAccountId());
        Account acc2 = getAccount("5007");
        assertEquals(acc2.getBalance().toString(), (balanceNow.add(new BigDecimal("1500"))).toString());
    }
    
    @Test
    public void testMakePayment() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException {
        // as long as credit account
        Account acc1 = getAccount("5007");
        BigDecimal balanceNow = acc1.getBalance();
        getTxService().makePayment(new BigDecimal("200"), "Payment 200", acc1.getAccountId());
        Account acc2 = getAccount("5007");
        assertEquals(acc2.getBalance().toString(), (balanceNow.subtract(new BigDecimal("200"))).toString());
    }
    
    @Test
    public void testTransferFunds() throws InvalidParameterException, AccountNotFoundException, InsufficientCreditException, InsufficientFundsException {
        Account accFrom1 = getAccount("5008");
        BigDecimal balanceFromNow = accFrom1.getBalance();
        
        Account accTo1 = getAccount("5007");
        BigDecimal balanceToNow = accTo1.getBalance();
        
        getTxService().transferFunds(new BigDecimal("200"), "Transfer 200", accFrom1.getAccountId(), accTo1.getAccountId());
        
        Account accFrom2 = getAccount("5008");
        assertEquals(accFrom2.getBalance().toString(), (balanceFromNow.subtract(new BigDecimal("200"))).toString());
        
        Account accTo2 = getAccount("5007");
        // because credit account - subtract
        assertEquals(accTo2.getBalance().toString(), (balanceToNow.subtract(new BigDecimal("200"))).toString());
    }
}
