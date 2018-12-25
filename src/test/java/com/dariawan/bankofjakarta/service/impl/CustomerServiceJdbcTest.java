package com.dariawan.bankofjakarta.service.impl;

import com.dariawan.bankofjakarta.service.CustomerService;
import com.dariawan.bankofjakarta.service.CustomerServiceTest;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:com/dariawan/bankofjakarta/**/spring-config-test.xml")
public class CustomerServiceJdbcTest extends CustomerServiceTest {

    @Autowired private CustomerService customerService;
    
    @Override
    public CustomerService getCustomerService() {
        return customerService;
    } 
    
}
