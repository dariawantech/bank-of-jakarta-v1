/**
 * Bank of Jakarta V1 (https://www.dariawan.com)
 * Copyright (C) 2018 Dariawan <hello@dariawan.com>
 *
 * PLEASE NOTE: Your use of this software is subject to the terms and conditions of the license agreement by which you acquired this software.
 * You may not use this software if you have not validly acquired a license for the software from Dariawan or its licensed distributors.
 */
package com.dariawan.bankofjakarta.service;

import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.exception.AccountNotFoundException;
import com.dariawan.bankofjakarta.exception.CustomerNotFoundException;
import com.dariawan.bankofjakarta.exception.IllegalAccountTypeException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import java.util.List;

public interface AccountService {
    // account creation and removal methods

    // makes a new account and enters it into db,
    // customer for customerId must exist 1st    
    public String createAccount(Account account, String customerId)
            throws CustomerNotFoundException, InvalidParameterException;

    // account removal methods
    public void removeAccount(String accountId)
            throws InvalidParameterException, AccountNotFoundException;

    // adds another customer to the account
    public void addCustomerToAccount(String customerId, String accountId)
            throws InvalidParameterException,
            CustomerNotFoundException, AccountNotFoundException;

    // removes a customer from the account, but
    // the customer is not removed from the db
    public void removeCustomerFromAccount(String customerId, String accountId)
            throws InvalidParameterException,
            CustomerNotFoundException, AccountNotFoundException;

    // returns an ArrayList of Account objects
    // that correspond to the accounts for the specified
    // customer
    public List<Account> getAccountsOfCustomer(String customerId)
            throws InvalidParameterException,
            CustomerNotFoundException;

    public List<String> getCustomerIds(String accountId)
            throws InvalidParameterException,
            AccountNotFoundException;

    public Account getDetails(String accountId)
            throws InvalidParameterException,
            AccountNotFoundException;
} // AccountService
