package com.dariawan.bankofjakarta.dao;

import com.dariawan.bankofjakarta.domain.NextId;
import com.dariawan.bankofjakarta.exception.db.FinderException;

public interface NextIdDao {
 
     NextId findByPrimaryKey(String beanName) throws FinderException;
}
