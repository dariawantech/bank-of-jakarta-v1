package com.dariawan.bankofjakarta.service.impl;

import com.dariawan.bankofjakarta.dao.AccountDao;
import com.dariawan.bankofjakarta.dao.NextIdDao;
import com.dariawan.bankofjakarta.dao.TxDao;
import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.domain.NextId;
import com.dariawan.bankofjakarta.domain.Tx;
import com.dariawan.bankofjakarta.exception.AccountNotFoundException;
import com.dariawan.bankofjakarta.exception.IllegalAccountTypeException;
import com.dariawan.bankofjakarta.exception.InsufficientCreditException;
import com.dariawan.bankofjakarta.exception.InsufficientFundsException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import com.dariawan.bankofjakarta.exception.TxNotFoundException;
import com.dariawan.bankofjakarta.service.TxService;
import com.dariawan.bankofjakarta.util.DomainUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.util.StringUtils;

public class TxServiceImpl implements TxService {

    private final BigDecimal bigZero = new BigDecimal("0.00");

    private TxDao txDao;
    private AccountDao accountDao;
    private NextIdDao nextIdDao;
    
    public void setTxDao(TxDao txDao) {
        this.txDao = txDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setNextIdDao(NextIdDao nextIdDao) {
        this.nextIdDao = nextIdDao;
    }
    
    public TxServiceImpl() {        
    }
    
    public TxServiceImpl(AccountDao accountDao, NextIdDao nextIdDao) {
        this.accountDao = accountDao;
        this.nextIdDao = nextIdDao;
    }

    public TxServiceImpl(TxDao txDao, AccountDao accountDao, NextIdDao nextIdDao) {
        this.txDao = txDao;
        this.accountDao = accountDao;
        this.nextIdDao = nextIdDao;
    }
        
    public List<Tx> getTxsOfAccount(Date startDate, Date endDate, String accountId)
            throws InvalidParameterException {

        List<Tx> txList = new ArrayList<>();

        if (startDate == null) {
            throw new InvalidParameterException("null startDate");
        }

        if (endDate == null) {
            throw new InvalidParameterException("null endDate");
        }

        if (accountId == null) {
            throw new InvalidParameterException("null accountId");
        }

        try {
            txList = txDao.findByAccountId(startDate, endDate, accountId);
        } catch (Exception ex) {
            return txList;
        }

        return txList;
    } // getTxsOfAccount

    public Tx getDetails(String txId)
            throws TxNotFoundException, InvalidParameterException {

        Tx tx;

        if (txId == null) {
            throw new InvalidParameterException("null txId");
        }

        try {
            tx = txDao.findByPrimaryKey(txId);
        } catch (Exception ex) {
            throw new TxNotFoundException(txId);
        }

        return tx;
    } // getDetails

    public void withdraw(BigDecimal amount, String description, String accountId)
            throws InvalidParameterException, AccountNotFoundException,
            IllegalAccountTypeException, InsufficientFundsException {

        Account account = checkAccountArgs(amount, description, accountId);

        String type = account.getType();

        if (DomainUtil.isCreditAccount(type)) {
            throw new IllegalAccountTypeException(type);
        }

        BigDecimal newBalance = account.getBalance().subtract(amount);

        if (newBalance.compareTo(bigZero) == -1) {
            throw new InsufficientFundsException();
        }

        executeTx(amount.negate(), description, newBalance, account);
    }

    public void deposit(BigDecimal amount, String description, String accountId)
            throws InvalidParameterException, AccountNotFoundException,
            IllegalAccountTypeException {

        Account account = checkAccountArgs(amount, description, accountId);

        String type = account.getType();

        if (DomainUtil.isCreditAccount(type)) {
            throw new IllegalAccountTypeException(type);
        }

        BigDecimal newBalance = account.getBalance().add(amount);
        executeTx(amount, description, newBalance, account);
    }

    public void makeCharge(BigDecimal amount, String description, String accountId)
            throws InvalidParameterException, AccountNotFoundException,
            IllegalAccountTypeException, InsufficientCreditException {

        Account account = checkAccountArgs(amount, description, accountId);

        String type = account.getType();

        if (DomainUtil.isCreditAccount(type) == false) {
            throw new IllegalAccountTypeException(type);
        }

        BigDecimal newBalance = account.getBalance().add(amount);

        if (newBalance.compareTo(account.getCreditLine()) == 1) {
            throw new InsufficientCreditException();
        }

        executeTx(amount, description, newBalance, account);
    }

    public void makePayment(BigDecimal amount, String description, String accountId)
            throws InvalidParameterException, AccountNotFoundException,
            IllegalAccountTypeException {

        Account account = checkAccountArgs(amount, description, accountId);

        String type = account.getType();

        if (DomainUtil.isCreditAccount(type) == false) {
            throw new IllegalAccountTypeException(type);
        }

        BigDecimal newBalance = account.getBalance().subtract(amount);
        executeTx(amount, description, newBalance, account);
    }

    public void transferFunds(BigDecimal amount, String description,
            String fromAccountId, String toAccountId)
            throws InvalidParameterException, AccountNotFoundException,
            InsufficientFundsException, InsufficientCreditException {
        Account fromAccount = checkAccountArgs(amount, description, fromAccountId);
        Account toAccount = checkAccountArgs(amount, description, toAccountId);

        String fromType = fromAccount.getType();
        BigDecimal fromBalance = fromAccount.getBalance();

        if (DomainUtil.isCreditAccount(fromType)) {
            BigDecimal fromNewBalance = fromBalance.add(amount);

            if (fromNewBalance.compareTo(fromAccount.getCreditLine()) == 1) {
                throw new InsufficientCreditException();
            }

            executeTx(amount, description, fromNewBalance, fromAccount);
        } else {
            BigDecimal fromNewBalance = fromBalance.subtract(amount);

            if (fromNewBalance.compareTo(bigZero) == -1) {
                throw new InsufficientFundsException();
            }

            executeTx(amount.negate(), description, fromNewBalance, fromAccount);
        }

        String toType = toAccount.getType();
        BigDecimal toBalance = toAccount.getBalance();

        if (DomainUtil.isCreditAccount(toType)) {
            BigDecimal toNewBalance = toBalance.subtract(amount);
            executeTx(amount.negate(), description, toNewBalance, toAccount);
        } else {
            BigDecimal toNewBalance = toBalance.add(amount);
            executeTx(amount, description, toNewBalance, toAccount);
        }
    } // transferFunds

    // private methods
    private void executeTx(BigDecimal amount, String description,
            BigDecimal newBalance, Account account) {

        // Set the new balance and create a new transaction       
        try {
            account.setBalance(newBalance);
            NextId nextId = nextIdDao.findByPrimaryKey("tx");
            Tx tx = new Tx(String.valueOf(nextId.getId()), account.getAccountId(), new Date(),
                    amount, newBalance, description);
            txDao.create(tx);
            accountDao.updateBalance(account);
        } catch (Exception ex) {
            throw new IllegalStateException("executeTx: " + ex.getMessage());
        }
    }

    private Account checkAccountArgs(BigDecimal amount,
            String description, String accountId)
            throws InvalidParameterException, AccountNotFoundException {
        Account account = null;

        if (StringUtils.isEmpty(description)) {
            throw new InvalidParameterException("null/empty description");
        }

        if (StringUtils.isEmpty(accountId)) {
            throw new InvalidParameterException("null/empty accountId");
        }

        if (amount == null || amount.compareTo(bigZero) != 1) {
            throw new InvalidParameterException("amount <= 0");
        }

        try {
            account = accountDao.findByPrimaryKey(accountId);
        } catch (Exception ex) {
            throw new AccountNotFoundException(accountId);
        }

        return account;
    } // checkAccountArgs
}
