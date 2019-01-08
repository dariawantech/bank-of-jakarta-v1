/**
 * Bank of Jakarta V1 (https://www.dariawan.com)
 * Copyright (C) 2018 Dariawan <hello@dariawan.com>
 *
 * PLEASE NOTE: Your use of this software is subject to the terms and conditions of the license agreement by which you acquired this software.
 * You may not use this software if you have not validly acquired a license for the software from Dariawan or its licensed distributors.
 */
package com.dariawan.bankofjakarta.service;

import com.dariawan.bankofjakarta.domain.Customer;
import com.dariawan.bankofjakarta.exception.AccountNotFoundException;
import com.dariawan.bankofjakarta.exception.CustomerNotFoundException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public abstract class CustomerServiceTest extends BaseServiceTest {    
    public abstract CustomerService getCustomerService();
    
    @Test
    public void createCustomer() throws InvalidParameterException {
        Customer customer = new Customer();
        customer.setLastName("Wayne");
        customer.setFirstName("Bruce");
        customer.setMiddleInitial("B");
        customer.setStreet("Sixth Avenue");
        customer.setCity("Gotham");
        customer.setState("DC");
        customer.setZip("12345");
        customer.setPhone("412-345-6789");
        customer.setEmail("batman@dc.com");

        getCustomerService().createCustomer(customer);
    }

    @Test
    public void testGetDetails() throws CustomerNotFoundException, InvalidParameterException {
        Customer customer = getCustomerService().getDetails("201");
        verifyCustomer(customer);
    }

    @Test
    public void testRemoveCustomer() throws CustomerNotFoundException, InvalidParameterException {
        Customer cust1 = getCustomerService().getDetails("200");
        verifyCustomer(cust1);
        
        try {
            getCustomerService().removeCustomer("200");
        }
        catch (CustomerNotFoundException ex) {
            // CustomerNotFoundException thrown since 200 not exists anymore
        }      
    }

    @Test
    public void testGetCustomersOfAccount() throws InvalidParameterException, AccountNotFoundException {
        List<Customer> customers = getCustomerService().getCustomersOfAccount("5008");
        Assert.assertNotNull(customers);
        Assert.assertFalse(customers.isEmpty());
    }

    @Test
    public void testGetCustomersOfLastName() throws InvalidParameterException {
        List<Customer> customers = getCustomerService().getCustomersOfLastName("Jones");
        Assert.assertNotNull(customers);
        Assert.assertFalse(customers.isEmpty());
    }

    @Test
    public void testSetName() throws CustomerNotFoundException, InvalidParameterException {
        String lastName = "Curry";
        String firstName = "Arthur";
        String middleInitial = "A";
        getCustomerService().setName(lastName, firstName, middleInitial, "200");
        
        Customer cust = getCustomerService().getDetails("200");
        Assert.assertNotNull(cust);
        Assert.assertEquals(cust.getLastName(), lastName);
        Assert.assertEquals(cust.getFirstName(), firstName);
        Assert.assertEquals(cust.getMiddleInitial(), middleInitial);
    }

    @Test
    public void testSetAddress() throws CustomerNotFoundException, InvalidParameterException {
        String street = "Atlantic Rd";
        String city = "Metropolis";
        String state = "DC";
        String zip = "45678";
        String phone = "456-789-0123";
        String email = "aquaman@dc.com";
        getCustomerService().setAddress(street, city, state, zip, phone, email, "200");
        
        Customer cust = getCustomerService().getDetails("200");
        Assert.assertNotNull(cust);
        Assert.assertEquals(cust.getStreet(), street);
        Assert.assertEquals(cust.getCity(), city);
        Assert.assertEquals(cust.getState(), state);
        Assert.assertEquals(cust.getZip(), zip);
        Assert.assertEquals(cust.getPhone(), phone);
        Assert.assertEquals(cust.getEmail(), email);
    }
}
