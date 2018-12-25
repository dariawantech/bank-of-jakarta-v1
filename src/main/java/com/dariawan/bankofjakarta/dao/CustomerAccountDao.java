package com.dariawan.bankofjakarta.dao;

import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.domain.Customer;

public interface CustomerAccountDao {
    
    void add(Customer customer, Account account);
    
    void removeByAccount(Account account);
    
    void removeByCustomer(Customer customer);    
    
    void removeCustomerFromAccount(Customer customer, Account account);
}
