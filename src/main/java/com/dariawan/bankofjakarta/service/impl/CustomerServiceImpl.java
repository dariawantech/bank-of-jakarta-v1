package com.dariawan.bankofjakarta.service.impl;

import com.dariawan.bankofjakarta.dao.AccountDao;
import com.dariawan.bankofjakarta.dao.CustomerAccountDao;
import com.dariawan.bankofjakarta.dao.CustomerDao;
import com.dariawan.bankofjakarta.dao.NextIdDao;
import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.domain.Customer;
import com.dariawan.bankofjakarta.domain.NextId;
import com.dariawan.bankofjakarta.exception.CustomerNotFoundException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import com.dariawan.bankofjakarta.exception.db.FinderException;
import com.dariawan.bankofjakarta.service.CustomerService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    
    private CustomerDao customerDao;
    private AccountDao accountDao;
    private NextIdDao nextIdDao;

    // customer creation and removal methods
    public String createCustomer(Customer customer)
        throws InvalidParameterException {
        // makes a new customer and enters it into db
        
        // System.out.println("CustomerServiceImpl createCustomer");

        if (customer.getLastName() == null) {
            throw new InvalidParameterException("null lastName");
        }

        if (customer.getFirstName() == null) {
            throw new InvalidParameterException("null firstName");
        }

        try {
            // System.out.println("CustomerServiceImpl creating nextId bean");
            NextId nextId = nextIdDao.findByPrimaryKey("customer");
            // System.out.println("Creating LocalCustomer with customerDao.create");
            
            // customer = customerDao.create(nextId.getNextId(),
            //         details.getLastName(), details.getFirstName(),
            //         details.getMiddleInitial(), details.getStreet(),
            //         details.getCity(), details.getState(), details.getZip(),
            //         details.getPhone(), details.getEmail());
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
        // System.out.println("CustomerServiceImpl removeCustomer");

        if (customerId == null) {
            throw new InvalidParameterException("null customerId");
        }

        try {
            Customer customer = customerDao.findByPrimaryKey(customerId);
            // customer.remove();
            customerDao.remove(customer);
        } catch (Exception ex) {
            throw new IllegalStateException("removeCustomer: " + ex.getMessage());
        }
    }

    // getters
    public Customer getDetails(String customerId)
        throws CustomerNotFoundException, InvalidParameterException {
        // returns the Customer for the specified customer
        // System.out.println("CustomerServiceImpl getDetails");

        Customer result;

        if (customerId == null) {
            throw new InvalidParameterException("null customerId");
        }

        try {
            result = customerDao.findByPrimaryKey(customerId);
            // result = new Customer(customer.getLastName(),
            //         customer.getFirstName(), customer.getMiddleInitial(),
            //         customer.getStreet(), customer.getCity(),
            //         customer.getState(), customer.getZip(),
            //         customer.getPhone(), customer.getEmail());
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
        // System.out.println("CustomerServiceImpl getCustomersOfAccount");

        List<Customer> customers = null;

        if (accountId == null) {
            throw new InvalidParameterException("null accountId");
        }

        try {
            Account account = accountDao.findByPrimaryKey(accountId);
            // customers = account.getCustomers();
            customers = customerDao.findByAccountId(account.getAccountId());
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }

        // return copyCustomersToDetails(customers);
        return customers;
    }

    public List<Customer> getCustomersOfLastName(String lastName)
        throws InvalidParameterException {
        // returns an ArrayList of Customer 
        // that correspond to the the lastName specified
        // returns null if no customers are found
        // System.out.println("CustomerServiceImpl getCustomersOfLastName");

        List<Customer> customers = null;

        if (lastName == null) {
            throw new InvalidParameterException("null lastName");
        }

        try {
            customers = customerDao.findByLastName(lastName);
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }

        // return copyCustomersToDetails(customers);
        return customers;
    }

    // setters
    public void setName(String lastName, String firstName,
        String middleInitial, String customerId)
        throws CustomerNotFoundException, InvalidParameterException {
        // System.out.println("CustomerServiceImpl setName");

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
        } catch (Exception ex) {
            throw new IllegalStateException("setName: " + ex.getMessage());
        }
    }

    public void setAddress(String street, String city, String state,
        String zip, String phone, String email, String customerId)
        throws CustomerNotFoundException, InvalidParameterException {
        // System.out.println("CustomerServiceImpl setAddress");

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

        // System.out.println("CustomerServiceImpl customerExists");

        try {
            customer = customerDao.findByPrimaryKey(customerId);            
        } catch (Exception ex) {
            return false;
        }

        return customer==null;
    }

    /*
    private ArrayList copyCustomersToDetails(Collection customers) {
        ArrayList detailsList = new ArrayList();
        Iterator i = customers.iterator();

        try {
            while (i.hasNext()) {
                Customer customer = (Customer) i.next();
                Customer details =
                    new Customer(customer.getCustomerId(),
                        customer.getLastName(), customer.getFirstName(),
                        customer.getMiddleInitial(), customer.getStreet(),
                        customer.getCity(), customer.getState(),
                        customer.getZip(), customer.getPhone(),
                        customer.getEmail());
                detailsList.add(details);
            }
        } catch (Exception ex) {
            throw new IllegalStateException(ex.getMessage());
        }

        return detailsList;
    }
    */
} // CustomerServiceImpl
