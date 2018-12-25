package com.dariawan.bankofjakarta.service;

import com.dariawan.bankofjakarta.domain.Customer;
import com.dariawan.bankofjakarta.exception.CustomerNotFoundException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import java.util.List;

public interface CustomerService {
    // customer creation and removal methods

    // makes a new customer and enters it into db,
    // returns customerId
    String createCustomer(Customer customer) throws InvalidParameterException;

    // removes customer from db
    void removeCustomer(String customerId)
            throws CustomerNotFoundException, InvalidParameterException;

    // getters
    
    // returns the details of a customer
    Customer getDetails(String customerId)
            throws CustomerNotFoundException, InvalidParameterException;

    // returns a List of Customer objects
    // that correspond to the customers for the specified account
    List<Customer> getCustomersOfAccount(String accountId)
            throws CustomerNotFoundException, InvalidParameterException;

    // returns a List of Customer objects
    // that correspond to the customers for the specified last name;
    // if now customers are found the List is empty
    List<Customer> getCustomersOfLastName(String lastName) throws InvalidParameterException;

    // setters
    void setName(String lastName, String firstName,
            String middleInitial, String customerId)
            throws CustomerNotFoundException, InvalidParameterException;

    void setAddress(String street, String city, String state,
            String zip, String phone, String email, String customerId)
            throws CustomerNotFoundException, InvalidParameterException;
} // CustomerService
