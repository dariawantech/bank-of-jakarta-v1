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
