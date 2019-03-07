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

import com.dariawan.bankofjakarta.dao.AccountDao;
import com.dariawan.bankofjakarta.dao.CustomerAccountDao;
import com.dariawan.bankofjakarta.dao.CustomerDao;
import com.dariawan.bankofjakarta.dao.NextIdDao;
import com.dariawan.bankofjakarta.dao.TxDao;
import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.domain.Customer;
import com.dariawan.bankofjakarta.domain.NextId;
import com.dariawan.bankofjakarta.exception.AccountNotFoundException;
import com.dariawan.bankofjakarta.exception.CustomerNotFoundException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import com.dariawan.bankofjakarta.exception.db.FinderException;
import com.dariawan.bankofjakarta.service.AccountService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.springframework.util.StringUtils;

public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao;
    private CustomerDao customerDao;
    private NextIdDao nextIdDao;
    private CustomerAccountDao customerAccountDao;
    private TxDao txDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void setNextIdDao(NextIdDao nextIdDao) {
        this.nextIdDao = nextIdDao;
    }

    public void setCustomerAccountDao(CustomerAccountDao customerAccountDao) {
        this.customerAccountDao = customerAccountDao;
    }

    public void setTxDao(TxDao txDao) {
        this.txDao = txDao;
    }
    
    public AccountServiceImpl() {        
    }
    
    public AccountServiceImpl(AccountDao accountDao, CustomerDao customerDao, 
            NextIdDao nextIdDao, CustomerAccountDao customerAccountDao, 
            TxDao txDao) {
        this.accountDao = accountDao;
        this.customerDao = customerDao;
        this.nextIdDao = nextIdDao;
        this.customerAccountDao = customerAccountDao;
        this.txDao = txDao;
    }
    
    // account creation and removal methods
    public String createAccount(Account account, String customerId)
            throws CustomerNotFoundException, InvalidParameterException {
        // makes a new account and enters it into db,
        Customer customer = null;

        if (StringUtils.isEmpty(account.getType())) {
            throw new InvalidParameterException("null/empty type");
        } else if (StringUtils.isEmpty(account.getDescription())) {
            throw new InvalidParameterException("null/empty description");
        } else if (account.getBeginBalanceTimeStamp() == null) {
            throw new InvalidParameterException("null beginBalanceTimeStamp");
        } else if (StringUtils.isEmpty(customerId)) {
            throw new InvalidParameterException("null/empty customerId");
        }

        try {
            customer = customerDao.findByPrimaryKey(customerId);
        } catch (FinderException ex) {
            throw new CustomerNotFoundException();
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }

        try {
            NextId nextId = nextIdDao.findByPrimaryKey("account");
            account.setAccountId(String.valueOf(nextId.getId()));
            
            account = accountDao.create(account);
            customerAccountDao.add(customer, account);
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }

        return account.getAccountId();
    }

    public void removeAccount(String accountId)
            throws InvalidParameterException, AccountNotFoundException {
        // removes account
        if (StringUtils.isEmpty(accountId)) {
            throw new InvalidParameterException("null/empty accountId");
        }

        try {
            Account account = accountDao.findByPrimaryKey(accountId);

            txDao.removeByAccount(account);
            customerAccountDao.removeByAccount(account);
            accountDao.remove(account);
        } catch (FinderException ex) {
            throw new AccountNotFoundException();
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }

    // customer-account relationship methods
    public void addCustomerToAccount(String customerId, String accountId)
            throws InvalidParameterException, CustomerNotFoundException,
            AccountNotFoundException {
        // adds another customer to the account
        Account account = null;

        if (StringUtils.isEmpty(customerId)) {
            throw new InvalidParameterException("null/empty customerId");
        } else if (StringUtils.isEmpty(accountId)) {
            throw new InvalidParameterException("null/empty accountId");
        }

        try {
            account = accountDao.findByPrimaryKey(accountId);
        } catch (FinderException ex) {
            throw new AccountNotFoundException();
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }

        try {
            Customer customer = customerDao.findByPrimaryKey(customerId);

            customerAccountDao.add(customer, account);
        } catch (FinderException ex) {
            throw new CustomerNotFoundException();
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }

    public void removeCustomerFromAccount(String customerId, String accountId)
            throws InvalidParameterException, CustomerNotFoundException,
            AccountNotFoundException {
        // removes a customer from this account, but
        // the customer is not removed from the db
        Account account = null;
        
        if (StringUtils.isEmpty(customerId)) {
            throw new InvalidParameterException("null/empty customerId");
        } else if (StringUtils.isEmpty(accountId)) {
            throw new InvalidParameterException("null/empty accountId");
        }

        try {
            account = accountDao.findByPrimaryKey(accountId);
        } catch (FinderException ex) {
            throw new AccountNotFoundException();
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }

        try {
            Customer customer = customerDao.findByPrimaryKey(customerId);
            customerAccountDao.removeCustomerFromAccount(customer, account);
        } catch (FinderException ex) {
            throw new CustomerNotFoundException();
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }
    }

    // getters
    public List<Account> getAccountsOfCustomer(String customerId)
            throws InvalidParameterException, CustomerNotFoundException {
        // returns an ArrayList of Account
        // that correspond to the accounts for the specified customer

        List<Account> accounts = null;
        
        if (StringUtils.isEmpty(customerId)) {
            throw new InvalidParameterException("null/empty customerId");
        }

        try {
            Customer customer = customerDao.findByPrimaryKey(customerId);
            accounts = accountDao.findByCustomerId(customer.getCustomerId());
        } catch (FinderException ex) {
            throw new CustomerNotFoundException();
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }

        return accounts;
    }

    public List<String> getCustomerIds(String accountId)
            throws InvalidParameterException, AccountNotFoundException {
        List<Customer> customers = null;

        if (StringUtils.isEmpty(accountId)) {
            throw new InvalidParameterException("null/empty accountId");
        }

        try {
            Account account = accountDao.findByPrimaryKey(accountId);
            customers = customerDao.findByAccountId(account.getAccountId());
        } catch (FinderException ex) {
            throw new AccountNotFoundException();
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }

        return copyCustomerIdsToArrayList(customers);
    }

    public Account getDetails(String accountId)
            throws InvalidParameterException, AccountNotFoundException {

        Account account = null;

        if (StringUtils.isEmpty(accountId)) {
            throw new InvalidParameterException("null/empty accountId");
        }

        try {
            account = accountDao.findByPrimaryKey(accountId);
        } catch (FinderException ex) {
            throw new AccountNotFoundException();
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }

        return account;
    }
    
    private List<String> copyCustomerIdsToArrayList(Collection customers) {
        ArrayList customerIdList = new ArrayList();
        Iterator i = customers.iterator();

        while (i.hasNext()) {
            Customer customer = (Customer) i.next();
            customerIdList.add(customer.getCustomerId());
        }

        return customerIdList;
    }
}
