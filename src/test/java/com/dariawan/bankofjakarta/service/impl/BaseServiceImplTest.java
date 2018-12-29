package com.dariawan.bankofjakarta.service.impl;

import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.exception.AccountNotFoundException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import com.dariawan.bankofjakarta.service.AccountService;
import com.dariawan.bankofjakarta.service.TxService;
import java.math.BigDecimal;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class BaseServiceImplTest {
    
    protected final BigDecimal bigZero = new BigDecimal("0.00");
    
    protected TxService txService;
    protected AccountService accountService;

    @Before
    public void setUp() {
        // Create the application from the configuration
        ApplicationContext context = new ClassPathXmlApplicationContext("com/dariawan/bankofjakarta/**/spring-config-test.xml");
        // Look up the application service interface
        txService = context.getBean("txService", TxService.class);
        accountService = context.getBean("accountService", AccountService.class);
    }
    
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
    
    protected Account getAccount(String accountId) {
        try {
            Account acc = accountService.getDetails(accountId);
            verifyAccount(acc);
            
            return acc;
        } catch (InvalidParameterException ex) {
            return null;
        } catch (AccountNotFoundException ex) {
            return null;
        }
    }
}
