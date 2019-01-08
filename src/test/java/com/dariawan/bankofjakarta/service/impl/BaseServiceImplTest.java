package com.dariawan.bankofjakarta.service.impl;

import com.dariawan.bankofjakarta.service.AccountService;
import com.dariawan.bankofjakarta.service.CustomerService;
import com.dariawan.bankofjakarta.service.TxService;
import java.math.BigDecimal;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class BaseServiceImplTest {
    
    protected final BigDecimal bigZero = new BigDecimal("0.00");
    
    protected TxService txService;
    protected AccountService accountService;
    protected CustomerService customerService;

    @Before
    public void setUp() {
        // Create the application from the configuration
        ApplicationContext context = new ClassPathXmlApplicationContext("com/dariawan/bankofjakarta/**/spring-config-test.xml");
        // Look up the application service interface
        txService = context.getBean("txService", TxService.class);
        accountService = context.getBean("accountService", AccountService.class);
        customerService = context.getBean("customerService", CustomerService.class);
    }
}
