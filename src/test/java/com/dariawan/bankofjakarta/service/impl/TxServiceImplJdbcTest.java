package com.dariawan.bankofjakarta.service.impl;

import com.dariawan.bankofjakarta.service.AccountService;
import com.dariawan.bankofjakarta.service.TxService;
import com.dariawan.bankofjakarta.service.TxServiceTest;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:com/dariawan/bankofjakarta/**/spring-config-test.xml")
public class TxServiceImplJdbcTest extends TxServiceTest {

    @Autowired private TxService txService;
    @Autowired private AccountService accountService;
    
    @Override
    public TxService getTxService() {
        return txService;
    }
    
    @Override
    public AccountService getAccountService() {
        return accountService;
    }
}
