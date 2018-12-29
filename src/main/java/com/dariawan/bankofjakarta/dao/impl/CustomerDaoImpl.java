package com.dariawan.bankofjakarta.dao.impl;

import com.dariawan.bankofjakarta.dao.CustomerDao;
import com.dariawan.bankofjakarta.domain.Customer;
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

public class CustomerDaoImpl implements CustomerDao {

    private static final String SQL_INSERT = "insert into CUSTOMER ("
            + "customer_id, last_name, first_name, middle_initial, "
            + "street, city, state, zip, phone, email) "
            + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_FIND_BY_CUSTOMER_ID = "select * from CUSTOMER where customer_id = ?";

    private static final String SQL_FIND_BY_ACCOUNT_ID = "select cus.* "
            + "from CUSTOMER_ACCOUNT_XREF cax "
            + "inner join CUSTOMER cus on cus.customer_id = cax.customer_id "
            + "where cax.account_id = ?";

    private static final String SQL_FIND_BY_LAST_NAME = "select * "
            + "from CUSTOMER where last_name = ?";

    private static final String SQL_DELETE = "delete from CUSTOMER where customer_id = ?";

    private static final String SQL_UPDATE_NAME = "update CUSTOMER "
            + "set last_name = :last_name, first_name = :first_name, middle_initial = :middle_initial "
            + "where customer_id = :customer_id";

    private static final String SQL_UPDATE_CONTACT = "update CUSTOMER "
            + "set street = :street, city = :city, state = :state, zip = :zip, "
            + "phone = :phone, email = :email "
            + "where customer_id = :customer_id";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public CustomerDaoImpl() {        
    }
    
    public CustomerDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }
    
    public Customer create(Customer customer) throws CreateException {
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(SQL_INSERT);
                ps.setString(1, customer.getCustomerId());
                ps.setString(2, customer.getLastName());
                ps.setString(3, customer.getFirstName());
                ps.setString(4, customer.getMiddleInitial());
                ps.setString(5, customer.getStreet());
                ps.setString(6, customer.getCity());
                ps.setString(7, customer.getState());
                ps.setString(8, customer.getZip());
                ps.setString(9, customer.getPhone());
                ps.setString(10, customer.getEmail());
                return ps;
            }
        });
        return customer;
    }

    public Customer findByPrimaryKey(String customerId) throws FinderException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_CUSTOMER_ID, new ResultSetCustomer(), customerId);
        } catch (EmptyResultDataAccessException err) {
            // EmptyResultDataAccessException thrown when a result was expected to have at least one row (or element) but zero rows (or elements) were actually returned.
            throw new FinderException(err.getMessage());
        }
    }

    public List<Customer> findByAccountId(String accountId) throws FinderException {
        List<Customer> result = jdbcTemplate.query(SQL_FIND_BY_ACCOUNT_ID, new ResultSetCustomer(), accountId);
        return result;
    }

    public List<Customer> findByLastName(String lastName) throws FinderException {
        List<Customer> result = jdbcTemplate.query(SQL_FIND_BY_LAST_NAME, new ResultSetCustomer(), lastName);
        return result;
    }

    public void remove(Customer customer) {
        jdbcTemplate.update(SQL_DELETE, customer.getCustomerId());
    }

    public void updateName(Customer customer) {
        SqlParameterSource customerParameter = new MapSqlParameterSource()
                .addValue("last_name", customer.getLastName())
                .addValue("first_name", customer.getFirstName())
                .addValue("middle_initial", customer.getMiddleInitial())
                .addValue("customer_id", customer.getCustomerId());
        namedParameterJdbcTemplate.update(SQL_UPDATE_NAME, customerParameter);
    }

    public void updateContact(Customer customer) {
        SqlParameterSource customerParameter = new MapSqlParameterSource()
                .addValue("street", customer.getStreet())
                .addValue("city", customer.getCity())
                .addValue("state", customer.getState())
                .addValue("zip", customer.getZip())
                .addValue("phone", customer.getPhone())
                .addValue("email", customer.getEmail())
                .addValue("customer_id", customer.getCustomerId());
        namedParameterJdbcTemplate.update(SQL_UPDATE_CONTACT, customerParameter);
    }

    private class ResultSetCustomer implements RowMapper<Customer> {

        @Override
        public Customer mapRow(ResultSet rs, int i) throws SQLException {
            Customer customer = new Customer();
            customer.setCustomerId(rs.getString("customer_id"));
            customer.setLastName(rs.getString("last_name"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setMiddleInitial(rs.getString("middle_initial"));
            customer.setStreet(rs.getString("street"));
            customer.setCity(rs.getString("city"));
            customer.setState(rs.getString("state"));
            customer.setZip(rs.getString("zip"));
            customer.setPhone(rs.getString("phone"));
            customer.setEmail(rs.getString("email"));
            return customer;
        }
    }
}
