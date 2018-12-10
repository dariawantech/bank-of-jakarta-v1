package com.dariawan.bankofjakarta;

import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.exception.AccountNotFoundException;
import com.dariawan.bankofjakarta.exception.InvalidParameterException;
import com.dariawan.bankofjakarta.service.AccountService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTestApp {

    public static void main(String[] args) {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("com/dariawan/bankofjakarta/spring-config.xml");

        AccountService accountService = appContext.getBean("accountService", AccountService.class);
        try {
            Account account = accountService.getDetails("5008");
        } catch (InvalidParameterException ex) {
            Logger.getLogger(SpringTestApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccountNotFoundException ex) {
            Logger.getLogger(SpringTestApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
