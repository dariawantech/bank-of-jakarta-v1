package com.dariawan.bankofjakarta.service;

import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.exception.AccountNotFoundException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import com.dariawan.bankofjakarta.service.AccountService;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public abstract class AccountServiceTest {
    public abstract AccountService getAccountService();
    
    @Test
    public void testGetDetails() throws InvalidParameterException, AccountNotFoundException {
        Account acc = getAccountService().getDetails("5008");
        verifyAccount(acc);
    }
    
    private void verifyAccount(Account acc) {
        assertNotNull(acc);
        assertNotNull(acc.getAccountId());
        assertNotNull(acc.getType());
        assertNotNull(acc.getDescription());
        assertNotNull(acc.getBalance());
        assertNotNull(acc.getCreditLine());
        assertNotNull(acc.getBeginBalance());
        assertNotNull(acc.getBeginBalanceTimeStamp());
    }
}
