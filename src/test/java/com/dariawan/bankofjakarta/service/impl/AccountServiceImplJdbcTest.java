/**
 * Bank of Jakarta V1 (https://www.dariawan.com)
 * Copyright (C) 2018 Dariawan <hello@dariawan.com>
 *
 * PLEASE NOTE: Your use of this software is subject to the terms and conditions of the license agreement by which you acquired this software.
 * You may not use this software if you have not validly acquired a license for the software from Dariawan or its licensed distributors.
 */
package com.dariawan.bankofjakarta.service.impl;

import com.dariawan.bankofjakarta.service.AccountService;
import com.dariawan.bankofjakarta.service.AccountServiceTest;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:com/dariawan/bankofjakarta/**/spring-config-test.xml")
public class AccountServiceImplJdbcTest extends AccountServiceTest {

    @Autowired private AccountService accountService;
    
    @Override
    public AccountService getAccountService() {
        return accountService;
    }    
}
