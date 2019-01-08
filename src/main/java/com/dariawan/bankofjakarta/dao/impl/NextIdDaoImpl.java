/**
 * Bank of Jakarta V1 (https://www.dariawan.com)
 * Copyright (C) 2018 Dariawan <hello@dariawan.com>
 *
 * PLEASE NOTE: Your use of this software is subject to the terms and conditions of the license agreement by which you acquired this software.
 * You may not use this software if you have not validly acquired a license for the software from Dariawan or its licensed distributors.
 */
package com.dariawan.bankofjakarta.dao.impl;

import com.dariawan.bankofjakarta.dao.NextIdDao;
import com.dariawan.bankofjakarta.domain.NextId;
import com.dariawan.bankofjakarta.exception.db.FinderException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class NextIdDaoImpl implements NextIdDao {
    
    private static final String SQL_FIND_BY_BEAN_NAME = "select * from NEXT_ID where beanName = ?";
    private static final String SQL_UPDATE_NEXT_ID = "update NEXT_ID set id = :id where beanName = :beanName";
    
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    
    public NextIdDaoImpl() {        
    }
    
    public NextIdDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }
    
    public NextId findByPrimaryKey(String beanName) throws FinderException {
        try {
            NextId nextId = jdbcTemplate.queryForObject(SQL_FIND_BY_BEAN_NAME, new ResultSetNextId(), beanName);
            
            int i = nextId.getId();
            i++;
            nextId.setId(i);
            
            SqlParameterSource nextIdParameter = new MapSqlParameterSource()
                    .addValue("id", nextId.getId())
                    .addValue("beanName", nextId.getBeanName());
            namedParameterJdbcTemplate.update(SQL_UPDATE_NEXT_ID, nextIdParameter);
            
            return nextId;
        } catch (EmptyResultDataAccessException err) {
            return null;
        }
    }
    
    private class ResultSetNextId implements RowMapper<NextId> {
            
        @Override
        public NextId mapRow(ResultSet rs, int i) throws SQLException {
            NextId nextId = new NextId();
            nextId.setBeanName(rs.getString("beanName"));
            nextId.setId(rs.getInt("id"));
            return nextId;
        }
    }
    
}
