/**
 * Bank of Jakarta v1 (https://www.dariawan.com)
 * Copyright (C) 2018 Dariawan <hello@dariawan.com>
 *
 * Creative Commons Attribution-ShareAlike 4.0 International License
 *
 * Under this license, you are free to:
 * # Share - copy and redistribute the material in any medium or format
 * # Adapt - remix, transform, and build upon the material for any purpose,
 *   even commercially.
 *
 * The licensor cannot revoke these freedoms
 * as long as you follow the license terms.
 *
 * License terms:
 * # Attribution - You must give appropriate credit, provide a link to the
 *   license, and indicate if changes were made. You may do so in any
 *   reasonable manner, but not in any way that suggests the licensor
 *   endorses you or your use.
 * # ShareAlike - If you remix, transform, or build upon the material, you must
 *   distribute your contributions under the same license as the original.
 * # No additional restrictions - You may not apply legal terms or
 *   technological measures that legally restrict others from doing anything the
 *   license permits.
 *
 * Notices:
 * # You do not have to comply with the license for elements of the material in
 *   the public domain or where your use is permitted by an applicable exception
 *   or limitation.
 * # No warranties are given. The license may not give you all of
 *   the permissions necessary for your intended use. For example, other rights
 *   such as publicity, privacy, or moral rights may limit how you use
 *   the material.
 *
 * You may obtain a copy of the License at
 *   https://creativecommons.org/licenses/by-sa/4.0/
 *   https://creativecommons.org/licenses/by-sa/4.0/legalcode
 */
package com.dariawan.bankofjakarta.dao.impl;

import com.dariawan.bankofjakarta.dao.TxDao;
import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.domain.Tx;
import com.dariawan.bankofjakarta.exception.db.CreateException;
import com.dariawan.bankofjakarta.exception.db.FinderException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

public class TxDaoImpl implements TxDao {

    private static final String SQL_INSERT = "insert into TX ("
            + "tx_id, account_id, time_stamp, amount, balance, description) "
            + "values (?, ?, ?, ?, ?, ?)";

    private static final String SQL_FIND_BY_TX_ID = "select * from TX where tx_id = ?";
    
    private static final String SQL_FIND_BY_ACCOUNT_ID = "select * from TX "
            + "where account_id = ? "
            + "and timestamp between ? and ?";
    
    private static final String SQL_DELETE_BY_ACCOUNT_ID = "delete from TX where account_id = ?";
    
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public TxDaoImpl() {        
    }
    
    public TxDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }
    
    public Tx create(Tx tx) throws CreateException {
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(SQL_INSERT);
                ps.setString(1, tx.getTxId());
                ps.setString(2, tx.getAccountId());
                ps.setDate(3, new java.sql.Date(tx.getTimeStamp().getTime()));
                ps.setBigDecimal(4, tx.getAmount());
                ps.setBigDecimal(5, tx.getBalance());
                ps.setString(6, tx.getDescription());
                return ps;
            }
        });
        return tx;
    }

    @Override
    public Tx findByPrimaryKey(String txId) throws FinderException {
        try {
            Tx tx = jdbcTemplate.queryForObject(SQL_FIND_BY_TX_ID, new ResultSetTx(), txId);
            return tx;
        } catch (EmptyResultDataAccessException err) {
            return null;
        }
    }

    @Override
    public List<Tx> findByAccountId(Date startDate, Date endDate, String accountId) throws FinderException {
        List<Tx> result = jdbcTemplate.query(SQL_FIND_BY_ACCOUNT_ID, new ResultSetTx(), accountId, startDate, endDate);
        return result;
    }

    private class ResultSetTx implements RowMapper<Tx> {

        @Override
        public Tx mapRow(ResultSet rs, int i) throws SQLException {
            String txId = rs.getString("tx_id");
            String accountId = rs.getString("account_id");
            Date timeStamp = rs.getDate("time_stamp");
            BigDecimal amount = rs.getBigDecimal("amount");
            BigDecimal balance = rs.getBigDecimal("balance");
            String description = rs.getString("begin_balance");
            
            Tx tx = new Tx(txId, accountId, timeStamp, amount, balance, description);
            return tx;
        }
    }
    
    public void removeByAccount(Account account) {
        jdbcTemplate.update(SQL_DELETE_BY_ACCOUNT_ID, account.getAccountId());
    }
}
