package com.zlikun.spring.conf;

import com.zaxxer.hikari.HikariDataSource;
import com.zlikun.spring.ds.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zlikun
 * @date 2018-09-19 19:15
 */
@Slf4j
@Configuration
public class DataSourceConfigure {

    /**
     * 从库节点数量(16，计数从0开始计)
     */
    private static final int slaveCount = 0xf;

    /**
     * 动态数据源，通过 DataSourceAspect 切面配置类来动态映射数据源<br>
     * !!! 不能在这里配置@Primary，会造成循环依赖问题
     *
     * @return
     */
    @Bean
    public DataSource dynamicDataSource() {

        DynamicDataSource ds = new DynamicDataSource();
        // 设置动态数据源
        Map<Object, Object> storage = new HashMap<>(32);
        storage.put("master", masterDataSource());
        // 真实从库数量
        int slaveNodes = 2;
        // 这里把真实从库按指定数量进行映射（可以理解为虚拟节点，可以据此实现权重等设置）
        for (int i = 0; i < slaveCount; i += slaveNodes) {
            storage.put("slave" + i, slaveDataSource1());
            storage.put("slave" + (i + 1), slaveDataSource2());
        }
        ds.setTargetDataSources(storage);

        // 设置默认数据源
        ds.setDefaultTargetDataSource(masterDataSource());

        return ds;
    }

    /**
     * 主数据源
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari.master")
    public DataSource masterDataSource() {
        log.info("--init master--");
        return new HikariDataSource();
    }

    /**
     * 从数据源
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari.slave1")
    public DataSource slaveDataSource1() {
        log.info("--init slave1--");
        return new HikariDataSource();
    }

    /**
     * 从数据源
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari.slave2")
    public DataSource slaveDataSource2() {
        log.info("--init slave2--");
        return new HikariDataSource();
    }

    /**
     * 在这里指定 DataSource，可以使用 @Primary，不会造成 DataSource 循环依赖
     *
     * @return
     */
    @Bean
    @Primary
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(masterDataSource());
    }


}
