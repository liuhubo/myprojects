package com.hello.dao;

import org.springframework.transaction.TransactionDefinition;

/**
 * 事务管理器<br>
 * 在事务开始的时候，切换至写数据源
 * 
 * @author mengchuang
 *
 */
public class DataSourceTransactionManager extends org.springframework.jdbc.datasource.DataSourceTransactionManager {

    /**
     * 
     */
    private static final long serialVersionUID = 9033051335133408655L;


    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        DBContextHolder.switchToWrite();// 事务全部在写库中执行
        super.doBegin(transaction, definition);
    }


    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        DBContextHolder.clear();
        super.doCleanupAfterCompletion(transaction);
    }
}
