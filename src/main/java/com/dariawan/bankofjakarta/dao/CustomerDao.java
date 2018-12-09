package com.dariawan.bankofjakarta.dao;

import com.dariawan.bankofjakarta.domain.Customer;
import com.dariawan.bankofjakarta.exception.db.CreateException;
import com.dariawan.bankofjakarta.exception.db.FinderException;
import java.util.List;

public interface CustomerDao {

    // Customer create(String customerId, String lastName,
    //         String firstName, String middleInitial, String street, String city,
    //         String state, String zip, String phone, String email)
    //         throws CreateException;
    Customer create(Customer customer) throws CreateException;
        
    Customer findByPrimaryKey(String customerId) throws FinderException;

    List<Customer> findByAccountId(String accountId) throws FinderException;

    List<Customer> findByLastName(String lastName) throws FinderException;
    
    void remove(Customer customer);
}
