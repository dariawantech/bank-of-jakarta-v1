package com.dariawan.bankofjakarta.service;

import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.exception.AccountNotFoundException;
import com.dariawan.bankofjakarta.exception.CustomerNotFoundException;
import com.dariawan.bankofjakarta.exception.IllegalAccountTypeException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public interface AccountService {

    // account creation and removal methods
    public String createAccount(Account account, String customerId)
            throws IllegalAccountTypeException,
            CustomerNotFoundException, InvalidParameterException;

    // makes a new account and enters it into db,
    // customer for customerId must exist 1st
    public void removeAccount(String accountId)
            throws InvalidParameterException,
            AccountNotFoundException;

    // removes account from db
    // customer-account relationship methods
    public void addCustomerToAccount(String customerId, String accountId)
            throws InvalidParameterException,
            CustomerNotFoundException, AccountNotFoundException;

    // adds another customer to the account
    public void removeCustomerFromAccount(String customerId, String accountId)
            throws InvalidParameterException,
            CustomerNotFoundException, AccountNotFoundException;

    // removes a customer from the account, but
    // the customer is not removed from the db
    public List<Account> getAccountsOfCustomer(String customerId)
            throws InvalidParameterException,
            CustomerNotFoundException;

    // returns an ArrayList of Account objects
    // that correspond to the accounts for the specified
    // customer
    public ArrayList getCustomerIds(String accountId)
            throws InvalidParameterException,
            AccountNotFoundException;

    public Account getDetails(String accountId)
            throws InvalidParameterException,
            AccountNotFoundException;
} // AccountService
