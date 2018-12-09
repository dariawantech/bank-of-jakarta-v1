package com.dariawan.bankofjakarta.dao.impl;

import com.dariawan.bankofjakarta.dao.CustomerAccountDao;
import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.domain.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

public class CustomerAccountDaoImpl implements CustomerAccountDao {

    private static final String SQL_INSERT = "insert into CUSTOMER_ACCOUNT_XREF ("
            + "customer_id, account_id) values (?, ?)";
    
    private static final String SQL_DELETE_BY_ACCOUNT_ID = "delete from CUSTOMER_ACCOUNT_XREF where account_id = ?";
    
    private static final String SQL_DELETE_BY_CUSOMER_N_ACCOUNT_ID = "delete from CUSTOMER_ACCOUNT_XREF where customer_id = ? and account_id = ?";
    
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public void add(Customer customer, Account account) {
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(SQL_INSERT);
                ps.setString(1, customer.getCustomerId());
                ps.setString(2, account.getAccountId());
                return ps;
            }
        });
    }

    public void removeByAccount(Account account) {
        jdbcTemplate.update(SQL_DELETE_BY_ACCOUNT_ID, account.getAccountId());
    }
    
    public void removeCustomerFromAccount(Customer customer, Account account) {
        jdbcTemplate.update(SQL_DELETE_BY_ACCOUNT_ID, customer.getCustomerId(), account.getAccountId());
    }
}
