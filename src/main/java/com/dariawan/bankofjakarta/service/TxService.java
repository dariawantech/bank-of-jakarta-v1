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

import com.dariawan.bankofjakarta.dao.AccountDao;
import com.dariawan.bankofjakarta.dao.NextIdDao;
import com.dariawan.bankofjakarta.dao.TxDao;
import com.dariawan.bankofjakarta.domain.Tx;
import com.dariawan.bankofjakarta.exception.AccountNotFoundException;
import com.dariawan.bankofjakarta.exception.IllegalAccountTypeException;
import com.dariawan.bankofjakarta.exception.InsufficientCreditException;
import com.dariawan.bankofjakarta.exception.InsufficientFundsException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import com.dariawan.bankofjakarta.exception.TxNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface TxService {
    
    void setTxDao(TxDao txDao);

    void setAccountDao(AccountDao accountDao);

    void setNextIdDao(NextIdDao nextIdDao);

    // getters
    
    // returns an ArrayList of Tx objects
    // that correspond to the txs for the specified account
    List<Tx> getTxsOfAccount(Date startDate, Date endDate, String accountId)
            throws InvalidParameterException;

    // returns the Tx for the specified tx
    // business transaction methods
    Tx getDetails(String txId)
            throws TxNotFoundException, InvalidParameterException;

    // withdraws funds from a non-credit account
    void withdraw(BigDecimal amount, String description, String accountId)
            throws InvalidParameterException,
            AccountNotFoundException, IllegalAccountTypeException,
            InsufficientFundsException;

    // deposits funds to a non-credit account
    void deposit(BigDecimal amount, String description, String accountId)
            throws InvalidParameterException,
            AccountNotFoundException, IllegalAccountTypeException;

    // transfers funds from one account to another
    void transferFunds(BigDecimal amount, String description,
            String fromAccountId, String toAccountId)
            throws InvalidParameterException,
            AccountNotFoundException, InsufficientFundsException,
            InsufficientCreditException;

    // makes a charge to a credit account
    void makeCharge(BigDecimal amount, String description, String accountId)
            throws InvalidParameterException, AccountNotFoundException,
            IllegalAccountTypeException, InsufficientCreditException;

    // makes a payment to a credit account
    void makePayment(BigDecimal amount, String description, String accountId)
            throws InvalidParameterException, AccountNotFoundException,
            IllegalAccountTypeException;    
}
