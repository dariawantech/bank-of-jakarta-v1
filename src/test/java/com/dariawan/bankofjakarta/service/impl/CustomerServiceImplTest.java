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
