package com.dariawan.bankofjakarta.service.impl;

import com.dariawan.bankofjakarta.dao.AccountDao;
import com.dariawan.bankofjakarta.dao.CustomerAccountDao;
import com.dariawan.bankofjakarta.dao.CustomerDao;
import com.dariawan.bankofjakarta.dao.NextIdDao;
import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.domain.Customer;
import com.dariawan.bankofjakarta.domain.NextId;
import com.dariawan.bankofjakarta.exception.AccountNotFoundException;
import com.dariawan.bankofjakarta.exception.CustomerNotFoundException;
import com.dariawan.bankofjakarta.exception.IllegalAccountTypeException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import com.dariawan.bankofjakarta.exception.db.FinderException;
import com.dariawan.bankofjakarta.service.AccountService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao;
    private CustomerDao customerDao;
    private NextIdDao nextIdDao;
    private CustomerAccountDao customerAccountDao;

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

    public AccountServiceImpl() {        
    }
    
    public AccountServiceImpl(AccountDao accountDao, CustomerDao customerDao, 
            NextIdDao nextIdDao, CustomerAccountDao customerAccountDao) {
        this.accountDao = accountDao;
        this.customerDao = customerDao;
        this.nextIdDao = nextIdDao;
        this.customerAccountDao = customerAccountDao;
    }
    
    // account creation and removal methods
    public String createAccount(Account account, String customerId)
            throws IllegalAccountTypeException, CustomerNotFoundException,
            InvalidParameterException {
        // makes a new account and enters it into db,
        Customer customer = null;

        if (account.getType() == null) {
            throw new InvalidParameterException("null type");
        } else if (account.getDescription() == null) {
            throw new InvalidParameterException("null description");
        } else if (account.getBeginBalanceTimeStamp() == null) {
            throw new InvalidParameterException("null beginBalanceTimeStamp");
        } else if (customerId == null) {
            throw new InvalidParameterException("null customerId");
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
        Account account = null;

        if (accountId == null) {
            throw new InvalidParameterException("null accountId");
        }

        try {
            account = accountDao.findByPrimaryKey(accountId);

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
        Customer customer = null;
        Account account = null;

        if (customerId == null) {
            throw new InvalidParameterException("null customerId");
        } else if (accountId == null) {
            throw new InvalidParameterException("null accountId");
        }

        try {
            account = accountDao.findByPrimaryKey(accountId);
        } catch (FinderException ex) {
            throw new AccountNotFoundException();
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }

        try {
            customer = customerDao.findByPrimaryKey(customerId);

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
        Customer customer = null;

        if (customerId == null) {
            throw new InvalidParameterException("null customerId");
        } else if (accountId == null) {
            throw new InvalidParameterException("null accountId");
        }

        try {
            account = accountDao.findByPrimaryKey(accountId);
        } catch (FinderException ex) {
            throw new AccountNotFoundException();
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }

        try {
            customer = customerDao.findByPrimaryKey(customerId);
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
        Customer customer = null;

        if (customerId == null) {
            throw new InvalidParameterException("null customerId");
        }

        try {
            customer = customerDao.findByPrimaryKey(customerId);
            accounts = accountDao.findByCustomerId(customer.getCustomerId());
        } catch (FinderException ex) {
            throw new CustomerNotFoundException();
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }

        return accounts;
    }

    public ArrayList getCustomerIds(String accountId)
            throws InvalidParameterException, AccountNotFoundException {
        List<Customer> customers = null;
        Account account = null;

        if (accountId == null) {
            throw new InvalidParameterException("null accountId");
        }

        try {
            account = accountDao.findByPrimaryKey(accountId);
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

        if (accountId == null) {
            throw new InvalidParameterException("null accountId");
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
    
    private ArrayList copyCustomerIdsToArrayList(Collection customers) {
        ArrayList customerIdList = new ArrayList();
        Iterator i = customers.iterator();

        while (i.hasNext()) {
            Customer customer = (Customer) i.next();
            customerIdList.add(customer.getCustomerId());
        }

        return customerIdList;
    }
}
