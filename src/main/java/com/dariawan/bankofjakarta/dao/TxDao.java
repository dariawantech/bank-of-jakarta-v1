package com.dariawan.bankofjakarta.dao;

import com.dariawan.bankofjakarta.domain.Tx;
import com.dariawan.bankofjakarta.exception.db.CreateException;
import com.dariawan.bankofjakarta.exception.db.FinderException;
import java.util.Date;
import java.util.List;

public interface TxDao {

    // Tx create(String txId, Account account, Date timeStamp,
    //         BigDecimal amount, BigDecimal balance, String description)
    //         throws CreateException;
    Tx create(Tx tx) throws CreateException;

    Tx findByPrimaryKey(String txId) throws FinderException;

    List<Tx> findByAccountId(Date startDate, Date endDate, String accountId)
            throws FinderException;
} // TxDao
