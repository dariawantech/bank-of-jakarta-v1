package com.dariawan.bankofjakarta.service;

import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.exception.AccountNotFoundException;
import com.dariawan.bankofjakarta.exception.IllegalAccountTypeException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import java.math.BigDecimal;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * 
 * abstract class TxServiceTest
 * extends AbstractTransactionalJUnit4SpringContextTests for transaction rollback
 */
public abstract class TxServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    public abstract TxService getTxService();
    public abstract AccountService getAccountService();
    
    private Account getAccount(String accountId) {
        try {
            Account acc = getAccountService().getDetails(accountId);
            assertNotNull(acc);
            assertNotNull(acc.getAccountId());
            
            return acc;
        } catch (InvalidParameterException ex) {
            return null;
        } catch (AccountNotFoundException ex) {
            return null;
        }
    }
    
    @Test
    public void testDeposit() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException {
        Account acc1 = getAccount("5008");
        BigDecimal balanceNow = acc1.getBalance();
        getTxService().deposit(new BigDecimal(100), "Deposit 100", acc1.getAccountId());
        Account acc2 = getAccount("5008");
        assertEquals(acc2.getBalance().toString(), (balanceNow.add(new BigDecimal(100))).toString());
    }
}
