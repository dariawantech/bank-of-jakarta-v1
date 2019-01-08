/**
 * Bank of Jakarta V1 (https://www.dariawan.com)
 * Copyright (C) 2018 Dariawan <hello@dariawan.com>
 *
 * PLEASE NOTE: Your use of this software is subject to the terms and conditions of the license agreement by which you acquired this software.
 * You may not use this software if you have not validly acquired a license for the software from Dariawan or its licensed distributors.
 */
package com.dariawan.bankofjakarta.dao;

import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.domain.Tx;
import com.dariawan.bankofjakarta.exception.db.CreateException;
import com.dariawan.bankofjakarta.exception.db.FinderException;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;

public interface TxDao {
    
    void setDataSource(DataSource dataSource);

    Tx create(Tx tx) throws CreateException;

    Tx findByPrimaryKey(String txId) throws FinderException;

    List<Tx> findByAccountId(Date startDate, Date endDate, String accountId)
            throws FinderException;
    
    void removeByAccount(Account account);
} // TxDao
