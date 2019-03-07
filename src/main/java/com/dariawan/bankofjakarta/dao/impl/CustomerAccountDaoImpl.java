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

import com.dariawan.bankofjakarta.dao.CustomerAccountDao;
import com.dariawan.bankofjakarta.domain.Account;
import com.dariawan.bankofjakarta.domain.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

public class CustomerAccountDaoImpl implements CustomerAccountDao {

    private static final String SQL_INSERT = "insert into CUSTOMER_ACCOUNT_XREF ("
            + "customer_id, account_id) values (?, ?)";
    
    private static final String SQL_DELETE_BY_ACCOUNT_ID = "delete from CUSTOMER_ACCOUNT_XREF where account_id = ?";
    
    private static final String SQL_DELETE_BY_CUSTOMER_ID = "delete from CUSTOMER_ACCOUNT_XREF where customer_id = ?";
    
    private static final String SQL_DELETE_BY_CUSTOMER_N_ACCOUNT_ID = "delete from CUSTOMER_ACCOUNT_XREF where customer_id = ? and account_id = ?";
    
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public CustomerAccountDaoImpl() {        
    }
    
    public CustomerAccountDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
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
    
    public void removeByCustomer(Customer customer) {
        jdbcTemplate.update(SQL_DELETE_BY_CUSTOMER_ID, customer.getCustomerId());
    }
    
    public void removeCustomerFromAccount(Customer customer, Account account) {
        jdbcTemplate.update(SQL_DELETE_BY_CUSTOMER_N_ACCOUNT_ID, customer.getCustomerId(), account.getAccountId());
    }
}
