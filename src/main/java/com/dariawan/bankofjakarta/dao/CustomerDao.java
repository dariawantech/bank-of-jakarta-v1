package com.dariawan.bankofjakarta.dao;

import com.dariawan.bankofjakarta.domain.Customer;
import com.dariawan.bankofjakarta.exception.CustomerNotFoundException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import com.dariawan.bankofjakarta.exception.db.CreateException;
import com.dariawan.bankofjakarta.exception.db.FinderException;
import java.util.List;

public interface CustomerDao {

    Customer create(Customer customer) throws CreateException;
        
    Customer findByPrimaryKey(String customerId) throws FinderException;

    List<Customer> findByAccountId(String accountId) throws FinderException;

    List<Customer> findByLastName(String lastName) throws FinderException;
    
    void remove(Customer customer);
    
    void updateName(Customer customer);

    void updateContact(Customer customer);
}
