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
import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.domain.Customer;
import com.dariawan.bankofjakarta.domain.NextId;
import com.dariawan.bankofjakarta.exception.AccountNotFoundException;
import com.dariawan.bankofjakarta.exception.CustomerNotFoundException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import com.dariawan.bankofjakarta.exception.db.FinderException;
import com.dariawan.bankofjakarta.service.CustomerService;
import java.util.List;
import org.springframework.util.StringUtils;

public class CustomerServiceImpl implements CustomerService {
    
    private CustomerDao customerDao;
    private AccountDao accountDao;
    private NextIdDao nextIdDao;
    private CustomerAccountDao customerAccountDao;

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setNextIdDao(NextIdDao nextIdDao) {
        this.nextIdDao = nextIdDao;
    }
    
    public void setCustomerAccountDao(CustomerAccountDao customerAccountDao) {
        this.customerAccountDao = customerAccountDao;
    }
    
    public CustomerServiceImpl() {        
    }
    
    public CustomerServiceImpl(CustomerDao customerDao, AccountDao accountDao, 
            NextIdDao nextIdDao, CustomerAccountDao customerAccountDao) {
        this.customerDao = customerDao;
        this.accountDao = accountDao;
        this.nextIdDao = nextIdDao;
        this.customerAccountDao = customerAccountDao;
    }
    
    // customer creation and removal methods
    public String createCustomer(Customer customer)
        throws InvalidParameterException {
        // makes a new customer and enters it into db
        
        if (StringUtils.isEmpty(customer.getLastName())) {
            throw new InvalidParameterException("null/empty lastName");
        }

        if (StringUtils.isEmpty(customer.getFirstName())) {
            throw new InvalidParameterException("null/empty firstName");
        }

        try {
            NextId nextId = nextIdDao.findByPrimaryKey("customer");
            customer.setCustomerId(String.valueOf(nextId.getId()));
            customer = customerDao.create(customer);
        } catch (Exception ex) {
            throw new IllegalStateException("createCustomer: " + ex.getMessage());
        }

        return customer.getCustomerId();
    }

    public void removeCustomer(String customerId)
        throws CustomerNotFoundException, InvalidParameterException {
        // removes customer from db
        
        if (StringUtils.isEmpty(customerId)) {
            throw new InvalidParameterException("null/empty customerId");
        }

        Customer customer;
        
        try {
            customer = customerDao.findByPrimaryKey(customerId);
        } catch (FinderException ex) {
            throw new CustomerNotFoundException();
        }
        
        try {
            customerAccountDao.removeByCustomer(customer);
            customerDao.remove(customer);
        } catch (Exception ex) {
            throw new IllegalStateException("removeCustomer: " + ex.getMessage());
        }
    }

    // getters
    public Customer getDetails(String customerId)
        throws CustomerNotFoundException, InvalidParameterException {
        // returns the Customer for the specified customer
        
        Customer result;

        if (StringUtils.isEmpty(customerId)) {
            throw new InvalidParameterException("null/empty customerId");
        }

        try {
            result = customerDao.findByPrimaryKey(customerId);
        } catch (FinderException ex) {
            throw new CustomerNotFoundException();
        } catch (Exception ex) {
            throw new IllegalStateException("getDetails: " + ex.getMessage());
        }

        return result;
    }

    public List<Customer> getCustomersOfAccount(String accountId)
        throws AccountNotFoundException, InvalidParameterException {
        // returns an ArrayList of Customer 
        // that correspond to the accountId specified
        
        List<Customer> customers = null;

        if (StringUtils.isEmpty(accountId)) {
            throw new InvalidParameterException("null/empty accountId");
        }

        Account account;
        try {
            account = accountDao.findByPrimaryKey(accountId);
        } catch (FinderException ex) {
            throw new AccountNotFoundException();
        }
        
        try {
            customers = customerDao.findByAccountId(account.getAccountId());
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }

        return customers;
    }

    public List<Customer> getCustomersOfLastName(String lastName)
        throws InvalidParameterException {
        // returns an ArrayList of Customer 
        // that correspond to the the lastName specified
        // returns null if no customers are found
        
        List<Customer> customers = null;

        if (StringUtils.isEmpty(lastName)) {
            throw new InvalidParameterException("null/empty lastName");
        }

        try {
            customers = customerDao.findByLastName(lastName);
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }

        return customers;
    }

    // setters
    public void setName(String lastName, String firstName,
        String middleInitial, String customerId)
        throws CustomerNotFoundException, InvalidParameterException {
        
        if (StringUtils.isEmpty(lastName)) {
            throw new InvalidParameterException("null/empty lastName");
        }

        if (StringUtils.isEmpty(firstName)) {
            throw new InvalidParameterException("null/empty firstName");
        }

        if (StringUtils.isEmpty(customerId)) {
            throw new InvalidParameterException("null/empty customerId");
        }

        if (customerExists(customerId) == false) {
            throw new CustomerNotFoundException(customerId);
        }

        try {
            Customer customer = customerDao.findByPrimaryKey(customerId);
            customer.setLastName(lastName);
            customer.setFirstName(firstName);
            customer.setMiddleInitial(middleInitial);
            customerDao.updateName(customer);
        } catch (FinderException ex) {
            throw new CustomerNotFoundException();
        } catch (Exception ex) {
            throw new IllegalStateException("setName: " + ex.getMessage());
        }
    }

    public void setAddress(String street, String city, String state,
        String zip, String phone, String email, String customerId)
        throws CustomerNotFoundException, InvalidParameterException {
        
        if (StringUtils.isEmpty(street)) {
            throw new InvalidParameterException("null/empty street");
        }

        if (StringUtils.isEmpty(city )) {
            throw new InvalidParameterException("null/empty city");
        }

        if (StringUtils.isEmpty(state)) {
            throw new InvalidParameterException("null/empty state");
        }

        if (StringUtils.isEmpty(customerId)) {
            throw new InvalidParameterException("null/empty customerId");
        }

        try {
            Customer customer = customerDao.findByPrimaryKey(customerId);
            customer.setStreet(street);
            customer.setCity(city);
            customer.setState(state);
            customer.setZip(zip);
            customer.setPhone(phone);
            customer.setEmail(email);
            customerDao.updateContact(customer);
        } catch (FinderException ex) {
            throw new CustomerNotFoundException(); 
        } catch (Exception ex) {
            throw new IllegalStateException("setAddress: " + ex.getMessage());
        }
    }

    // private methods
    private boolean customerExists(String customerId) {
        // If a business method has been invoked with
        // a different customerId, then update the
        // customerId and customer variables.
        // Return null if the customer is not found.
        Customer customer;

        try {
            customer = customerDao.findByPrimaryKey(customerId);            
        } catch (Exception ex) {
            return false;
        }

        return customer!=null;
    }
} // CustomerServiceImpl
