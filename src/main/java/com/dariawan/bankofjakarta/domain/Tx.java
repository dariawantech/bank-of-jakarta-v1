/**
 * Bank of Jakarta V1 (https://www.dariawan.com)
 * Copyright (C) 2018 Dariawan <hello@dariawan.com>
 *
 * PLEASE NOTE: Your use of this software is subject to the terms and conditions of the license agreement by which you acquired this software.
 * You may not use this software if you have not validly acquired a license for the software from Dariawan or its licensed distributors.
 */
package com.dariawan.bankofjakarta.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Tx implements Serializable {

    private final String txId;
    private final String accountId;
    private final Date timeStamp;
    private final BigDecimal amount;
    private final BigDecimal balance;
    private final String description;

    public Tx(String txId, String accountId, Date timeStamp, BigDecimal amount,
            BigDecimal balance, String description) {
        this.txId = txId;
        this.accountId = accountId;
        this.timeStamp = timeStamp;
        this.amount = amount;
        this.balance = balance;
        this.description = description;
    }

    // getters
    public String getTxId() {
        return txId;
    }

    public String getAccountId() {
        return accountId;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getDescription() {
        return description;
    }
} // Tx
