package com.sym.myboot.config.DynamicDataSourceConfig;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

public class DynamicDataSourceTransactionManager extends DataSourceTransactionManager {

    protected void doBegin(Object transaction, TransactionDefinition definition){
        boolean readOnly = definition.isReadOnly();
        if(readOnly){
            DynamicDataSourceHolder.putDataSource(DynamicDataSourceGlobal.READ);
        }else{
            DynamicDataSourceHolder.putDataSource(DynamicDataSourceGlobal.WRITE);
        }
        super.doBegin(transaction,definition);
    }

    protected void doCleanupAfterCompletion(Object  transaction){
        super.doCleanupAfterCompletion(transaction);
        DynamicDataSourceHolder.clearDataSource();
    }
}
