package com.dariawan.bankofjakarta.dao;

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
} // TxDao
