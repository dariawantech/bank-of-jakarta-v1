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
