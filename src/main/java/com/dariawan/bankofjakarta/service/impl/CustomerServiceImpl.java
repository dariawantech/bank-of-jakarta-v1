package com.dariawan.bankofjakarta.service.impl;

import com.dariawan.bankofjakarta.dao.AccountDao;
import com.dariawan.bankofjakarta.dao.CustomerDao;
import com.dariawan.bankofjakarta.dao.NextIdDao;
import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.domain.Customer;
import com.dariawan.bankofjakarta.domain.NextId;
import com.dariawan.bankofjakarta.exception.CustomerNotFoundException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import com.dariawan.bankofjakarta.exception.db.FinderException;
import com.dariawan.bankofjakarta.service.CustomerService;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    
    private CustomerDao customerDao;
    private AccountDao accountDao;
    private NextIdDao nextIdDao;

    // customer creation and removal methods
    public String createCustomer(Customer customer)
        throws InvalidParameterException {
        // makes a new customer and enters it into db
        
        if (customer.getLastName() == null) {
            throw new InvalidParameterException("null lastName");
        }

        if (customer.getFirstName() == null) {
            throw new InvalidParameterException("null firstName");
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
        
        if (customerId == null) {
            throw new InvalidParameterException("null customerId");
        }

        try {
            Customer customer = customerDao.findByPrimaryKey(customerId);
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

        if (customerId == null) {
            throw new InvalidParameterException("null customerId");
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
        throws InvalidParameterException {
        // returns an ArrayList of Customer 
        // that correspond to the accountId specified
        
        List<Customer> customers = null;

        if (accountId == null) {
            throw new InvalidParameterException("null accountId");
        }

        try {
            Account account = accountDao.findByPrimaryKey(accountId);
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

        if (lastName == null) {
            throw new InvalidParameterException("null lastName");
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
        
        if (lastName == null) {
            throw new InvalidParameterException("null lastName");
        }

        if (firstName == null) {
            throw new InvalidParameterException("null firstName");
        }

        if (customerId == null) {
            throw new InvalidParameterException("null customerId");
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
        } catch (Exception ex) {
            throw new IllegalStateException("setName: " + ex.getMessage());
        }
    }

    public void setAddress(String street, String city, String state,
        String zip, String phone, String email, String customerId)
        throws CustomerNotFoundException, InvalidParameterException {
        
        if (street == null) {
            throw new InvalidParameterException("null street");
        }

        if (city == null) {
            throw new InvalidParameterException("null city");
        }

        if (state == null) {
            throw new InvalidParameterException("null state");
        }

        if (customerId == null) {
            throw new InvalidParameterException("null customerId");
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

        return customer==null;
    }    
} // CustomerServiceImpl
