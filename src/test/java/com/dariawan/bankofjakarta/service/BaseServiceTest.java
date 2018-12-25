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
