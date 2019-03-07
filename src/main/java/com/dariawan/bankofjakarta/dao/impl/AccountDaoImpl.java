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

import com.dariawan.bankofjakarta.dao.AccountDao;
import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.exception.db.CreateException;
import com.dariawan.bankofjakarta.exception.db.FinderException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class AccountDaoImpl implements AccountDao {

    private static final String SQL_INSERT = "insert into ACCOUNT ("
            + "account_id, type, description, balance, credit_line, "
            + "begin_balance, begin_balance_time_stamp) "
            + "values (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_FIND_BY_ACCOUNT_ID = "select * from ACCOUNT where account_id = ?";

    private static final String SQL_FIND_BY_CUSTOMER_ID = "select acc.* "
            + "from CUSTOMER_ACCOUNT_XREF cax "
            + "inner join ACCOUNT acc on acc.account_id = cax.account_id "
            + "where cax.customer_id = ?";
    
    private static final String SQL_DELETE = "delete from ACCOUNT where account_id = ?";
    
    private static final String SQL_UPDATE_BALANCE = "update ACCOUNT "
            + "set balance = :balance where account_id = :account_id";
    
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public AccountDaoImpl() {        
    }
    
    public AccountDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }
    
    public Account create(Account account) throws CreateException {
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(SQL_INSERT);
                ps.setString(1, account.getAccountId());
                ps.setString(2, account.getType());
                ps.setString(3, account.getDescription());
                ps.setBigDecimal(4, account.getBalance());
                ps.setBigDecimal(5, account.getCreditLine());
                ps.setBigDecimal(6, account.getBeginBalance());
                ps.setDate(7, new java.sql.Date(account.getBeginBalanceTimeStamp().getTime()));
                return ps;
            }
        });
        return account;
    }

    public Account findByPrimaryKey(String accountId) throws FinderException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ACCOUNT_ID, new ResultSetAccount(), accountId);
        } catch (EmptyResultDataAccessException err) {
            // EmptyResultDataAccessException thrown when a result was expected to have at least one row (or element) but zero rows (or elements) were actually returned.
            throw new FinderException(err.getMessage());
        }
    }

    public List<Account> findByCustomerId(String customerId) throws FinderException {
        List<Account> result = jdbcTemplate.query(SQL_FIND_BY_CUSTOMER_ID, new ResultSetAccount(), customerId);
        return result;
    }

    public void remove(Account account) {
        jdbcTemplate.update(SQL_DELETE, account.getAccountId());
    }
    
    public void updateBalance(Account account) {
        SqlParameterSource accountParameter = new MapSqlParameterSource()
                .addValue("balance", account.getBalance())
                .addValue("account_id", account.getAccountId());
        namedParameterJdbcTemplate.update(SQL_UPDATE_BALANCE, accountParameter);
    }
    
    private class ResultSetAccount implements RowMapper<Account> {

        @Override
        public Account mapRow(ResultSet rs, int i) throws SQLException {
            Account account = new Account();
            account.setAccountId(rs.getString("account_id"));
            account.setType(rs.getString("type"));
            account.setDescription(rs.getString("description"));
            account.setBalance(rs.getBigDecimal("balance"));
            account.setCreditLine(rs.getBigDecimal("credit_line"));
            account.setBeginBalance(rs.getBigDecimal("begin_balance"));
            account.setBeginBalanceTimeStamp(rs.getDate("begin_balance_time_stamp"));
            return account;
        }
    }
}
