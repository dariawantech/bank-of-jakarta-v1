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
    List<Tx> getTxsOfAccount(Date startDate, Date endDate, String accountId)
            throws InvalidParameterException;

    // returns an ArrayList of Tx objects
    // that correspond to the txs for the specified account
    Tx getDetails(String txId)
            throws TxNotFoundException, InvalidParameterException;

    // returns the Tx for the specified tx
    // business transaction methods
    void withdraw(BigDecimal amount, String description, String accountId)
            throws InvalidParameterException,
            AccountNotFoundException, IllegalAccountTypeException,
            InsufficientFundsException;

    // withdraws funds from a non-credit account
    void deposit(BigDecimal amount, String description, String accountId)
            throws InvalidParameterException,
            AccountNotFoundException, IllegalAccountTypeException;

    // deposits funds to a non-credit account
    void transferFunds(BigDecimal amount, String description,
            String fromAccountId, String toAccountId)
            throws InvalidParameterException,
            AccountNotFoundException, InsufficientFundsException,
            InsufficientCreditException;

    // transfers funds from one account to another
    void makeCharge(BigDecimal amount, String description, String accountId)
            throws InvalidParameterException, AccountNotFoundException,
            IllegalAccountTypeException, InsufficientCreditException;

    // makes a charge to a credit account
    void makePayment(BigDecimal amount, String description, String accountId)
            throws InvalidParameterException, AccountNotFoundException,
            IllegalAccountTypeException;

    // makes a payment to a credit account
}
