package com.dariawan.bankofjakarta.dao;

import com.dariawan.bankofjakarta.domain.NextId;
import com.dariawan.bankofjakarta.exception.db.FinderException;
import javax.sql.DataSource;

public interface NextIdDao {
    
    void setDataSource(DataSource dataSource);
    
    NextId findByPrimaryKey(String beanName) throws FinderException;
}
