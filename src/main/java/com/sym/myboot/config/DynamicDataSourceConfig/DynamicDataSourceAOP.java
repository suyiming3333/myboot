package com.sym.myboot.config.DynamicDataSourceConfig;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(0)
public class DynamicDataSourceAOP {

    @Before("execution(* com.sym.myboot.service.*.*(..))")
    public void process(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        if(methodName.startsWith("get")
                || methodName.startsWith("select")
                || methodName.startsWith("find")){
            DynamicDataSourceHolder.putDataSource(DynamicDataSourceGlobal.READ);
        }else{
            DynamicDataSourceHolder.putDataSource(DynamicDataSourceGlobal.WRITE);
        }
    }
}