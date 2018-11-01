package com.sym.myboot.config.DynamicDataSourceConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Primary
public class DynamicDataSource extends AbstractRoutingDataSource {

    public Object getWriteDataSource() {
        return writeDataSource;
    }

    public void setWriteDataSource(Object writeDataSource) {
        this.writeDataSource = writeDataSource;
    }

    public Object getReadDataSource() {
        return readDataSource;
    }

    public void setReadDataSource(Object readDataSource) {
        this.readDataSource = readDataSource;
    }


    @Autowired
    @Qualifier("writeDataSource")
    private Object writeDataSource;

    @Autowired
    @Qualifier("readDataSource")
    private Object readDataSource;

    @Override
    protected Object determineCurrentLookupKey() {
        DynamicDataSourceGlobal dynamicDataSourceGlobal = DynamicDataSourceHolder.getDataSource();

        if(dynamicDataSourceGlobal ==null || dynamicDataSourceGlobal == DynamicDataSourceGlobal.WRITE){
            return DynamicDataSourceGlobal.WRITE.name();
        }
        return DynamicDataSourceGlobal.READ.name();
    }

    public void afterPropertiesSet(){
        if(this.writeDataSource == null){
            throw new IllegalArgumentException("Property 'writerDataSource' is required");
        }
        setDefaultTargetDataSource(writeDataSource);
        Map<Object,Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DynamicDataSourceGlobal.WRITE.name(),writeDataSource);
        if(readDataSource !=null){
            targetDataSource.put(DynamicDataSourceGlobal.READ.name(),readDataSource);
        }
        setTargetDataSources(targetDataSource);
        super.afterPropertiesSet();
    }
}
