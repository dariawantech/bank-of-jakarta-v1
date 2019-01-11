/**
 * Bank of Jakarta V1 (https://www.dariawan.com)
 * Copyright (C) 2018 Dariawan <hello@dariawan.com>
 *
 * PLEASE NOTE: Your use of this software is subject to the terms and conditions of the license agreement by which you acquired this software.
 * You may not use this software if you have not validly acquired a license for the software from Dariawan or its licensed distributors.
 */
package com.dariawan.bankofjakarta;

import com.dariawan.bankofjakarta.dao.AccountDao;
import com.dariawan.bankofjakarta.dao.NextIdDao;
import com.dariawan.bankofjakarta.dao.TxDao;
import com.dariawan.bankofjakarta.dao.impl.AccountDaoImpl;
import com.dariawan.bankofjakarta.dao.impl.NextIdDaoImpl;
import com.dariawan.bankofjakarta.dao.impl.TxDaoImpl;
import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.exception.AccountNotFoundException;
import com.dariawan.bankofjakarta.exception.IllegalAccountTypeException;
import com.dariawan.bankofjakarta.exception.InsufficientCreditException;
import com.dariawan.bankofjakarta.exception.InsufficientFundsException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import com.dariawan.bankofjakarta.service.AccountService;
import com.dariawan.bankofjakarta.service.TxService;
import com.dariawan.bankofjakarta.service.impl.TxServiceImpl;
import java.math.BigDecimal;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTestApp {

    void testBeanConfiguration() {
        // Create the application from the configuration
        ApplicationContext appContext = new ClassPathXmlApplicationContext("com/dariawan/bankofjakarta/spring-config.xml");

        /* Accessing service */
        // Classic way: cast is needed
        TxService ts1 = (TxService) appContext.getBean("txService");
        // Since Spring 3.0: no more cast, type is a method param
        TxService ts2 = appContext.getBean("txService", TxService.class);
        // New in Spring 3.0: No need for bean id if type is unique
        TxService ts3 = appContext.getBean(TxService.class );

        /* Accessing Dao */
        // Classic way: cast is needed
        TxDao td1 = (TxDao) appContext.getBean("txDao");
        // Use typed method to avoid cast
        TxDao td2 = appContext.getBean("txDao", TxDao.class);
        // No need for bean id if type is unique
        TxDao td3 = appContext.getBean(TxDao.class );
        
        assert ts1!=null;
        assert ts2!=null;
        assert ts3!=null;

        assert td1!=null;
        assert td2!=null;
        assert td3!=null;        
    }
    
    void testBeanConstructor() {
        // Create the application from the configuration
        ApplicationContext appContext = new ClassPathXmlApplicationContext("com/dariawan/bankofjakarta/spring-config.xml");

        // get from context to help define dataSource - we not do this 'manually'
        javax.sql.DataSource dataSource = appContext.getBean("dataSource", javax.sql.DataSource.class );

        AccountDao accountDao = new AccountDaoImpl(dataSource);
        NextIdDao nextIdDao = new NextIdDaoImpl(dataSource);
        TxDao txDao = new TxDaoImpl(dataSource);

        TxService txService = new TxServiceImpl(txDao, accountDao, nextIdDao);
        try {
            // Use the service
            txService.withdraw(new BigDecimal("100"), "Withdraw 100$", "5008");
        } catch (InvalidParameterException | AccountNotFoundException | IllegalAccountTypeException | InsufficientFundsException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }
    
    void testBeanSetter() {
        // Create the application from the configuration
        ApplicationContext appContext = new ClassPathXmlApplicationContext("com/dariawan/bankofjakarta/spring-config.xml");

        // get from context to help define dataSource - we not do this 'manually'
        javax.sql.DataSource dataSource = appContext.getBean("dataSource", javax.sql.DataSource.class );

        AccountDao accountDao = new AccountDaoImpl();
        accountDao.setDataSource(dataSource);

        NextIdDao nextIdDao = new NextIdDaoImpl();
        nextIdDao.setDataSource(dataSource);

        TxDao txDao = new TxDaoImpl(dataSource);
        txDao.setDataSource(dataSource);

        TxService txService = new TxServiceImpl();
        txService.setAccountDao(accountDao);
        txService.setNextIdDao(nextIdDao);
        txService.setTxDao(txDao);

        try {
            // Use the service
            txService.withdraw(new BigDecimal("100"), "Withdraw 100$", "5008");
        } catch (InvalidParameterException | AccountNotFoundException | IllegalAccountTypeException | InsufficientFundsException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }
    
     void testBeanMixConstructorSetter() {
        // Create the application from the configuration
        ApplicationContext appContext = new ClassPathXmlApplicationContext("com/dariawan/bankofjakarta/spring-config.xml");

        // get from context to help define dataSource - we not do this 'manually'
        javax.sql.DataSource dataSource = appContext.getBean("dataSource", javax.sql.DataSource.class );

        AccountDao accountDao = new AccountDaoImpl(dataSource);
        NextIdDao nextIdDao = new NextIdDaoImpl(dataSource);

        TxDao txDao = new TxDaoImpl(dataSource);
        txDao.setDataSource(dataSource);

        TxService txService = new TxServiceImpl(accountDao, nextIdDao);
        txService.setTxDao(txDao);

        try {
            // Use the service
            txService.withdraw(new BigDecimal("100"), "Withdraw 100$", "5008");
        } catch (InvalidParameterException | AccountNotFoundException | IllegalAccountTypeException | InsufficientFundsException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }
     
    void testBeanConfigurationAndFunction() {
        // Create the application from the configuration
        ApplicationContext appContext = new ClassPathXmlApplicationContext("com/dariawan/bankofjakarta/spring-config.xml");

        AccountService accountService = appContext.getBean("accountService", AccountService.class);
        try {
            Account account = accountService.getDetails("5008");
        } catch (InvalidParameterException|AccountNotFoundException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        // Look up the application service interface
        /*
        TxService txService = appContext.getBean("txService", TxService.class);
        try {
            // Use the service
            txService.withdraw(new BigDecimal("100"), "Withdraw 100$", "5008");
        } catch (InvalidParameterException | AccountNotFoundException | IllegalAccountTypeException | InsufficientFundsException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        */
    }
    
    void testMultipleBeanConfigurationAndFunction() {
        // Create the application from the multiple configuration
        ApplicationContext appContext = new ClassPathXmlApplicationContext(
                "com/dariawan/bankofjakarta/spring-tx-config.xml",
                "com/dariawan/bankofjakarta/spring-dao-config.xml",
                "com/dariawan/bankofjakarta/spring-service-config.xml",
                "com/dariawan/bankofjakarta/spring-aop-config.xml");

        AccountService accountService = appContext.getBean("accountService", AccountService.class);
        try {
            Account account = accountService.getDetails("5007");
            System.out.println(account.getBalance());
        } catch (InvalidParameterException|AccountNotFoundException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }
    
    void testTransferFunds() {
        ApplicationContext appContext = new ClassPathXmlApplicationContext(
                "com/dariawan/bankofjakarta/spring-config.xml");

        TxService txService = appContext.getBean("txService", TxService.class);
        try {
            txService.transferFunds(new BigDecimal("200"), "Transfer 200", "5008", "5007");
        } catch (InvalidParameterException|AccountNotFoundException|
                InsufficientFundsException|InsufficientCreditException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }        
    }
    
    public static void main(String[] args) {
        SpringTestApp app = new SpringTestApp();
        app.testBeanConfiguration();
        // app.testBeanConstructor();
        // app.testBeanSetter();
        // app.testBeanMixConstructorSetter();
        }
}
