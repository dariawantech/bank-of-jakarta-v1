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

import com.dariawan.bankofjakarta.exception.AccountNotFoundException;
import com.dariawan.bankofjakarta.exception.IllegalAccountTypeException;
import com.dariawan.bankofjakarta.exception.InsufficientCreditException;
import com.dariawan.bankofjakarta.exception.InsufficientFundsException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import java.math.BigDecimal;
import org.junit.Test;

public class TxServiceImplTest extends BaseServiceImplTest {
    
    @Test(expected=InvalidParameterException.class)
    public void testCheckAccountArgsAmount0() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException {
        // cannot deposit 0
        txService.deposit(BigDecimal.ZERO, "Deposit 0", "5008");        
    }
    
    @Test(expected=InvalidParameterException.class)
    public void testCheckAccountArgsNullDescription() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException {
        // cannot deposit 0
        txService.deposit(new BigDecimal("100"), null, "5008");        
    }
    
    @Test(expected=InvalidParameterException.class)
    public void testCheckAccountArgsEmptyAccountId() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException {
        // cannot deposit 0
        txService.deposit(new BigDecimal("100"), "Deposit 100", "");        
    }
    
    @Test(expected=AccountNotFoundException.class)
    public void testCheckAccountArgsAccountNotFoundException() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException {
        // account 5009 not exists
        txService.deposit(new BigDecimal("100"), "Deposit 100", "5009");        
    }
    
    @Test(expected=IllegalAccountTypeException.class)
    public void testDepositIllegalAccountTypeException() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException {
        // 5007 is credit account
        txService.deposit(new BigDecimal("100"), "Deposit 100", "5007");        
    }
    
    @Test(expected=InsufficientFundsException.class)
    public void testWithdrawInsufficientFundsException() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException, InsufficientFundsException {
        // as long as not credit account
        txService.withdraw(new BigDecimal("100000"), "Withdraw 100000", "5008");
    }
    
    @Test(expected=IllegalAccountTypeException.class)
    public void testWithdrawIllegalAccountTypeException() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException, InsufficientFundsException {
        // 5007 is credit account
        txService.withdraw(new BigDecimal("1000"), "Withdraw 1000", "5007");
    }
    
    @Test(expected=IllegalAccountTypeException.class)
    public void testMakeChargeIllegalAccountTypeException() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException, InsufficientCreditException {
        // as long as not credit account
        txService.makeCharge(new BigDecimal("1500"), "Charge 1500", "5008");
    }
    
    @Test(expected=InsufficientCreditException.class)
    public void testMakeChargeInsufficientCreditException() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException, InsufficientCreditException {
        // 5007 is credit account
        txService.makeCharge(new BigDecimal("5000"), "Charge 5000", "5007");
    }
    
    @Test(expected=IllegalAccountTypeException.class)
    public void testMakePaymentIllegalAccountTypeException() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException {
        // as long as not credit account
        txService.makePayment(new BigDecimal("200"), "Payment 200", "5008");
    }
    
    @Test(expected=InsufficientCreditException.class)
    public void testTransferFundsInsufficientCreditException() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException, InsufficientCreditException, InsufficientFundsException {
        // 5007 is credit account, credit line 5000
        txService.transferFunds(new BigDecimal("5000"), "Transfer 5000", "5007", "5008");
    }
    
    @Test(expected=InsufficientFundsException.class)
    public void testTransferFundsInsufficientFundsException() throws InvalidParameterException, AccountNotFoundException, InsufficientCreditException, InsufficientFundsException {
        // 5008 is savings account
        txService.transferFunds(new BigDecimal("100000"), "Transfer 100000", "5008", "5007");
    }
}
