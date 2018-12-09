package com.dariawan.bankofjakarta.dao;

import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.exception.db.CreateException;
import com.dariawan.bankofjakarta.exception.db.FinderException;
import java.util.List;

public interface AccountDao {

    // Account create(String accountId, String type,
    //         String description, BigDecimal balance, BigDecimal creditLine,
    //         BigDecimal beginBalance, Date beginBalanceTimeStamp)
    //        throws CreateException;
    Account create(Account account) throws CreateException;
    
    Account findByPrimaryKey(String accountId) throws FinderException;

    List<Account> findByCustomerId(String customerId) throws FinderException;
    
    void remove(Account account);
}
