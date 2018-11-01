package com.sym.myboot.config.DynamicDataSourceConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Primary
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Value("${spring.datasource.readSize}")
    private int dataSourceNumber;

    private AtomicInteger count = new AtomicInteger(0);


    @Autowired
    @Qualifier("writeDataSource")
    private Object writeDataSource;

    @Autowired
    @Qualifier("readDataSource1")
    private Object readDataSource1;

    @Autowired
    @Qualifier("readDataSource2")
    private Object readDataSource2;

    @Override
    protected Object determineCurrentLookupKey() {
        Object lookupKey = null;
        DynamicDataSourceGlobal dynamicDataSourceGlobal = DynamicDataSourceHolder.getDataSource();
        //数据库标识为空或者为写库
        if(dynamicDataSourceGlobal ==null || dynamicDataSourceGlobal == DynamicDataSourceGlobal.WRITE){
            lookupKey = DynamicDataSourceGlobal.WRITE.name();
        }else{
            int number = count.getAndAdd(1);
            int lookupIndex = number % dataSourceNumber;
            lookupKey = "readDataSource"+"1";
        }
        return lookupKey;
    }

    public void afterPropertiesSet(){
        if(this.writeDataSource == null){
            throw new IllegalArgumentException("Property 'writerDataSource' is required");
        }
        setDefaultTargetDataSource(writeDataSource);
        Map<Object,Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DynamicDataSourceGlobal.WRITE.name(),writeDataSource);

        if(readDataSource1 !=null){
            targetDataSource.put(DynamicDataSourceGlobal.READ.name()+"1",readDataSource1);
        }
        if(readDataSource2 !=null){
            targetDataSource.put(DynamicDataSourceGlobal.READ.name()+"2",readDataSource2);
        }
        setTargetDataSources(targetDataSource);
        super.afterPropertiesSet();
    }
}
