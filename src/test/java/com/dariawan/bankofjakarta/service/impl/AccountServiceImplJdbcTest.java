package com.dariawan.bankofjakarta.service.impl;

import com.dariawan.bankofjakarta.service.AccountService;
import com.dariawan.bankofjakarta.service.AccountServiceTest;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:com/dariawan/bankofjakarta/spring-cfg.xml")
public class AccountServiceImplJdbcTest extends AccountServiceTest {

    @Autowired private AccountService accountService;
    
    @Override
    public AccountService getAccountService() {
        return accountService;
    }
    
}
