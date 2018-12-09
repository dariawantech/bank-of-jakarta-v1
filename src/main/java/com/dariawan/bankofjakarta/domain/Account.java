package com.dariawan.bankofjakarta.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * This class holds the details of a bank account entity. It contains getters
 * and setters for each variable.
 */
public class Account implements Serializable {

    private String accountId;
    private String type;
    private String description;
    private BigDecimal balance;
    private BigDecimal creditLine;
    private BigDecimal beginBalance;
    private Date beginBalanceTimeStamp;
    
    public Account() {        
    }

    public Account(String accountId, String type, String description,
            BigDecimal balance, BigDecimal creditLine, BigDecimal beginBalance,
            Date beginBalanceTimeStamp) {
        this.accountId = accountId;
        this.type = type;
        this.description = description;
        this.balance = balance;
        this.creditLine = creditLine;
        this.beginBalance = beginBalance;
        this.beginBalanceTimeStamp = beginBalanceTimeStamp;
    }

    public Account(String type, String description, BigDecimal balance,
            BigDecimal creditLine, BigDecimal beginBalance,
            Date beginBalanceTimeStamp) {
        // this.accountId = accountId;
        this.type = type;
        this.description = description;
        this.balance = balance;
        this.creditLine = creditLine;
        this.beginBalance = beginBalance;
        this.beginBalanceTimeStamp = beginBalanceTimeStamp;
    }

    // getters
    public String getAccountId() {
        return accountId;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getCreditLine() {
        return creditLine;
    }

    public BigDecimal getBeginBalance() {
        return beginBalance;
    }

    public Date getBeginBalanceTimeStamp() {
        return beginBalanceTimeStamp;
    }

    public BigDecimal getRemainingCredit() {
        return creditLine.subtract(balance);
    }

    // setters
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setCreditLine(BigDecimal creditLine) {
        this.creditLine = creditLine;
    }

    public void setBeginBalance(BigDecimal beginBalance) {
        this.beginBalance = beginBalance;
    }

    public void setBeginBalanceTimeStamp(Date beginBalanceTimeStamp) {
        this.beginBalanceTimeStamp = beginBalanceTimeStamp;
    }
}
