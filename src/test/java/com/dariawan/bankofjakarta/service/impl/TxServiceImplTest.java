package com.dariawan.bankofjakarta.service.impl;

import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.exception.AccountNotFoundException;
import com.dariawan.bankofjakarta.exception.IllegalAccountTypeException;
import com.dariawan.bankofjakarta.exception.InsufficientCreditException;
import com.dariawan.bankofjakarta.exception.InsufficientFundsException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import com.dariawan.bankofjakarta.service.AccountService;
import com.dariawan.bankofjakarta.service.TxService;
import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TxServiceImplTest {
    private TxService txService;
    private AccountService accountService;

    @Before
    public void setUp() {
        // Create the application from the configuration
        ApplicationContext context = new ClassPathXmlApplicationContext("com/dariawan/bankofjakarta/**/spring-config-test.xml");
        // Look up the application service interface
        txService = context.getBean("txService", TxService.class);
        accountService = context.getBean("accountService", AccountService.class);
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
    
    private Account getAccount(String accountId) {
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
    
    // @Test
    public void testDeposit() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException {
        // as long as not credit account
        Account acc1 = getAccount("5008");
        BigDecimal balanceNow = acc1.getBalance();
        txService.deposit(new BigDecimal("100"), "Deposit 100", acc1.getAccountId());
        Account acc2 = getAccount("5008");
        assertEquals(acc2.getBalance().toString(), (balanceNow.add(new BigDecimal("100"))).toString());
    }
    
    @Test(expected=InvalidParameterException.class)
    public void testCheckAccountArgsAmount0() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException {
        // cannot deposit 0
        txService.deposit(BigDecimal.ZERO, "Deposit 0", "5008");        
    }
    
    @Test(expected=InvalidParameterException.class)
    public void testCheckAccountArgsNullDescription() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException {
        // cannot deposit 0
        txService.deposit(new BigDecimal("100"), null, "5008");        
    }
    
    @Test(expected=InvalidParameterException.class)
    public void testCheckAccountArgsEmptyAccountId() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException {
        // cannot deposit 0
        txService.deposit(new BigDecimal("100"), "Deposit 100", "");        
    }
    
    @Test(expected=AccountNotFoundException.class)
    public void testCheckAccountArgsAccountNotFoundException() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException {
        // account 5009 not exists
        txService.deposit(new BigDecimal("100"), "Deposit 100", "5009");        
    }
    
    @Test(expected=IllegalAccountTypeException.class)
    public void testDepositIllegalAccountTypeException() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException {
        // 5007 is credit account
        txService.deposit(new BigDecimal("100"), "Deposit 100", "5007");        
    }
    
    @Test(expected=InsufficientFundsException.class)
    public void testWithdrawInsufficientFundsException() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException, InsufficientFundsException {
        // as long as not credit account
        txService.withdraw(new BigDecimal("100000"), "Withdraw 100000", "5008");
    }
    
    @Test(expected=IllegalAccountTypeException.class)
    public void testWithdrawIllegalAccountTypeException() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException, InsufficientFundsException {
        // 5007 is credit account
        txService.withdraw(new BigDecimal("1000"), "Withdraw 1000", "5007");
    }
    
    @Test(expected=IllegalAccountTypeException.class)
    public void testMakeChargeIllegalAccountTypeException() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException, InsufficientCreditException {
        // as long as not credit account
        txService.makeCharge(new BigDecimal("1500"), "Charge 1500", "5008");
    }
    
    @Test(expected=InsufficientCreditException.class)
    public void testMakeChargeInsufficientCreditException() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException, InsufficientCreditException {
        // 5007 is credit account
        txService.makeCharge(new BigDecimal("5000"), "Charge 5000", "5007");
    }
    
    @Test(expected=IllegalAccountTypeException.class)
    public void testMakePaymentIllegalAccountTypeException() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException {
        // as long as not credit account
        txService.makePayment(new BigDecimal("200"), "Payment 200", "5008");
    }
    
    @Test(expected=InsufficientCreditException.class)
    public void testTransferFundsInsufficientCreditException() throws InvalidParameterException, AccountNotFoundException, IllegalAccountTypeException, InsufficientCreditException, InsufficientFundsException {
        // 5007 is credit account, credit line 5000
        txService.transferFunds(new BigDecimal("5000"), "Transfer 5000", "5007", "5008");
    }
    
    @Test(expected=InsufficientFundsException.class)
    public void testTransferFundsInsufficientFundsException() throws InvalidParameterException, AccountNotFoundException, InsufficientCreditException, InsufficientFundsException {
        // 5008 is savings account
        txService.transferFunds(new BigDecimal("100000"), "Transfer 100000", "5008", "5007");
    }
}
