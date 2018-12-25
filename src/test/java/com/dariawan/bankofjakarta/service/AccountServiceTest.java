package com.dariawan.bankofjakarta.service;

import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.exception.AccountNotFoundException;
import com.dariawan.bankofjakarta.exception.CustomerNotFoundException;
import com.dariawan.bankofjakarta.exception.IllegalAccountTypeException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import org.junit.Test;

/**
 * 
 * abstract class AccountServiceTest 
 */
public abstract class AccountServiceTest extends BaseServiceTest {
    private final BigDecimal bigZero = new BigDecimal("0.00");
    public abstract AccountService getAccountService();
    
    @Test
    public void testGetDetails() throws InvalidParameterException, AccountNotFoundException {
        Account acc = getAccountService().getDetails("5008");
        verifyAccount(acc);
    }
    
    @Test
    public void testCreateAccount() throws IllegalAccountTypeException, CustomerNotFoundException, InvalidParameterException {
        Account account = new Account();
        account.setType("Savings");
        account.setDescription("Savings Account");
        account.setBalance(bigZero);
        account.setCreditLine(bigZero);
        account.setBeginBalance(bigZero);
        account.setBeginBalanceTimeStamp(new GregorianCalendar(118, 12, 24, 11, 10, 10).getTime());
        
        getAccountService().createAccount(account, "200");
    }
    
    @Test
    public void testRemoveAccount() throws InvalidParameterException, AccountNotFoundException {
        getAccountService().removeAccount("5005");        
    }
         
    @Test
    public void testAddCustomerToAccount() throws InvalidParameterException, CustomerNotFoundException, AccountNotFoundException {
        getAccountService().addCustomerToAccount("200", "5005");
    }

    @Test
    public void testRemoveCustomerFromAccount() throws InvalidParameterException, CustomerNotFoundException, AccountNotFoundException {
        getAccountService().removeCustomerFromAccount("200", "5005");
    }
    
    @Test
    public void testGetAccountsOfCustomer() throws InvalidParameterException, CustomerNotFoundException {
        getAccountService().getAccountsOfCustomer("200");
    }

    @Test
    public void testGetCustomerIds() throws InvalidParameterException, AccountNotFoundException {
        getAccountService().getCustomerIds("5005");
    }    
}
