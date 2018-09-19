package com.zlikun.spring.ds;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 数据源切面配置，使用注解配置哪些方法使用master，哪些方法使用slave，通过一个计数器对数据源进行路由，实现简单的负载均衡
 *
 * @author zlikun
 * @date 2018-09-19 19:29
 */
@Aspect
@Component
public class DataSourceAspect {

    /**
     * 从库节点数量
     */
    private static final int slaveCount = 0xf;
    /**
     * 从库选择计数器
     */
    private static final AtomicLong slaveSelector = new AtomicLong();

    /**
     * 主库名
     */
    private static final String DS_MASTER_NAME = "master";

    @Pointcut("@annotation(com.zlikun.spring.ds.DataSourceSlave)")
    private void slave() {
    }

    @Pointcut("@annotation(com.zlikun.spring.ds.DataSourceMaster)")
    private void master() {
    }

    @Around("master()")
    public Object masterAround(ProceedingJoinPoint joinPoint) throws Throwable {
        DynamicDataSourceHolder.setDataSourceKey(DS_MASTER_NAME);
        try {
            return joinPoint.proceed();
        } finally {
            DynamicDataSourceHolder.clearDataSourceKey();
        }
    }

    @Around("slave()")
    public Object slaveAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取当前数据源，如果不是master，则使用选择计数器选择一个从库数据源使用
        if (!DynamicDataSourceHolder.isMaster()) {
            // 在[0, 0xf]区间内循环
            DynamicDataSourceHolder.setDataSourceKey("slave" + (slaveSelector.getAndIncrement() & slaveCount));
        }
        try {
            return joinPoint.proceed();
        } finally {
            DynamicDataSourceHolder.clearDataSourceKey();
        }
    }
}
