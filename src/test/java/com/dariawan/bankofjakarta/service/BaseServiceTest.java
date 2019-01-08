/**
 * Bank of Jakarta V1 (https://www.dariawan.com)
 * Copyright (C) 2018 Dariawan <hello@dariawan.com>
 *
 * PLEASE NOTE: Your use of this software is subject to the terms and conditions of the license agreement by which you acquired this software.
 * You may not use this software if you have not validly acquired a license for the software from Dariawan or its licensed distributors.
 */
package com.dariawan.bankofjakarta.service;

import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.domain.Customer;
import static org.junit.Assert.assertNotNull;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * 
 * extends AbstractTransactionalJUnit4SpringContextTests for transaction rollback
 */
public abstract class BaseServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    
    protected void verifyAccount(Account acc) {
        assertNotNull(acc);
        assertNotNull(acc.getAccountId());
        assertNotNull(acc.getType());
        assertNotNull(acc.getDescription());
        assertNotNull(acc.getBalance());
        assertNotNull(acc.getCreditLine());
        assertNotNull(acc.getBeginBalance());
        assertNotNull(acc.getBeginBalanceTimeStamp());
    }
    
    protected void verifyCustomer(Customer cust) {
        assertNotNull(cust);
        assertNotNull(cust.getCustomerId());
        assertNotNull(cust.getLastName());
        assertNotNull(cust.getFirstName());
        assertNotNull(cust.getCity());
        assertNotNull(cust.getPhone());
        assertNotNull(cust.getEmail());
    }
}
