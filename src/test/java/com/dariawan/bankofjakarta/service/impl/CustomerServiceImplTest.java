package com.dariawan.bankofjakarta.service.impl;

import com.dariawan.bankofjakarta.domain.Customer;
import com.dariawan.bankofjakarta.exception.AccountNotFoundException;
import com.dariawan.bankofjakarta.exception.CustomerNotFoundException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import org.junit.Test;

public class CustomerServiceImplTest extends BaseServiceImplTest {

    @Test(expected=InvalidParameterException.class)
    public void createCustomerEmptyLastName() throws InvalidParameterException {
        Customer customer = new Customer();
        customer.setLastName("");
        customer.setFirstName("Bruce");
        
        customerService.createCustomer(customer);
    }

    @Test(expected=InvalidParameterException.class)
    public void createCustomerEmptyFirstName() throws InvalidParameterException {
        Customer customer = new Customer();
        customer.setLastName("Wayne");
        customer.setFirstName("");
        
        customerService.createCustomer(customer);
    }
    
    @Test(expected=InvalidParameterException.class)
    public void testGetDetailsInvalidParameterException() throws CustomerNotFoundException, InvalidParameterException {
        customerService.getDetails(null);
    }

    @Test(expected=CustomerNotFoundException.class)
    public void testGetDetailsCustomerNotFoundException() throws CustomerNotFoundException, InvalidParameterException {
        customerService.getDetails("210");
    }
    
    @Test(expected=InvalidParameterException.class)
    public void testRemoveCustomerInvalidParameterException() throws CustomerNotFoundException, InvalidParameterException {
        customerService.removeCustomer("");
    }
    
    @Test(expected=CustomerNotFoundException.class)
    public void testRemoveCustomerCustomerNotFoundException() throws CustomerNotFoundException, InvalidParameterException {
        customerService.removeCustomer("210");
    }

    @Test(expected=InvalidParameterException.class)
    public void testGetCustomersOfAccountInvalidParameterException() throws InvalidParameterException, AccountNotFoundException {
        customerService.getCustomersOfAccount(null);
    }
    
    @Test(expected=AccountNotFoundException.class)
    public void testGetCustomersOfAccountAccountNotFoundException() throws InvalidParameterException, AccountNotFoundException {
        customerService.getCustomersOfAccount("5010");
    }

    @Test(expected=InvalidParameterException.class)
    public void testGetCustomersOfLastNameInvalidParameterException() throws InvalidParameterException {
        customerService.getCustomersOfLastName("");
    }

    @Test(expected=InvalidParameterException.class)
    public void testSetNameEmptyLastName() throws CustomerNotFoundException, InvalidParameterException {
        customerService.setName("", "Arthur", "", "200");
    }

    @Test(expected=InvalidParameterException.class)
    public void testSetNameNullFirstName() throws CustomerNotFoundException, InvalidParameterException {
        customerService.setName("Curry", null, null, "200");
    }
    
    @Test(expected=InvalidParameterException.class)
    public void testSetNameEmptyCustomerId() throws CustomerNotFoundException, InvalidParameterException {
        customerService.setName("Curry", "Arthur", null, "");
    }
    
    @Test(expected=CustomerNotFoundException.class)
    public void testSetNameCustomerNotFoundException() throws CustomerNotFoundException, InvalidParameterException {
        customerService.setName("Curry", "Arthur", null, "210");
    }
    
    @Test(expected=InvalidParameterException.class)
    public void testSetAddressEmptyStreet() throws CustomerNotFoundException, InvalidParameterException {
        customerService.setAddress("", "Metropolis", "DC", "45678", "456-789-0123", "aquaman@dc.com", "200");
    }
    
    @Test(expected=InvalidParameterException.class)
    public void testSetAddressEmptyCity() throws CustomerNotFoundException, InvalidParameterException {
        customerService.setAddress("Atlantic Rd", "", "DC", "45678", "456-789-0123", "aquaman@dc.com", "200");
    }
    
    @Test(expected=InvalidParameterException.class)
    public void testSetAddressNullState() throws CustomerNotFoundException, InvalidParameterException {
        customerService.setAddress("Atlantic Rd", "Metropolis", null, "45678", "456-789-0123", "aquaman@dc.com", "200");
    }
    
    @Test(expected=InvalidParameterException.class)
    public void testSetAddressNullCustomerId() throws CustomerNotFoundException, InvalidParameterException {
        customerService.setAddress("Atlantic Rd", "Metropolis", "DC", "45678", "456-789-0123", "aquaman@dc.com", null);
    }
    
    @Test(expected=CustomerNotFoundException.class)
    public void testSetAddressCustomerNotFoundException() throws CustomerNotFoundException, InvalidParameterException {
        customerService.setAddress("Atlantic Rd", "Metropolis", "DC", "45678", "456-789-0123", "aquaman@dc.com", "210");
    }
}
