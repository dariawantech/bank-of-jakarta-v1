package com.dariawan.bankofjakarta.dao;

import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.domain.Customer;
import java.util.ArrayList;
import java.util.List;

public interface CustomerAccountDao {
    
    void add(Customer customer, Account account);
    
    void removeByAccount(Account account);
    
    void removeCustomerFromAccount(Customer customer, Account account);
}
