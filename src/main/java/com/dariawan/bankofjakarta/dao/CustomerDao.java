/**
 * Bank of Jakarta V1 (https://www.dariawan.com)
 * Copyright (C) 2018 Dariawan <hello@dariawan.com>
 *
 * PLEASE NOTE: Your use of this software is subject to the terms and conditions of the license agreement by which you acquired this software.
 * You may not use this software if you have not validly acquired a license for the software from Dariawan or its licensed distributors.
 */
package com.dariawan.bankofjakarta.dao;

import com.dariawan.bankofjakarta.domain.Customer;
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
