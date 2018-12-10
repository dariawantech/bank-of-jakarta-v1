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
            Account account = jdbcTemplate.queryForObject(SQL_FIND_BY_ACCOUNT_ID, new ResultSetAccount(), accountId);
            return account;
        } catch (EmptyResultDataAccessException err) {
            return null;
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
                .addValue("customer_id", account.getAccountId());
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
