package com.zlikun.spring.dao;

import com.zlikun.spring.ds.DataSourceMaster;
import com.zlikun.spring.ds.DataSourceSlave;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * @author zlikun
 * @date 2018-09-19 21:01
 */
@Slf4j
@Repository
public class UserDao {

    /**
     * 使用slave数据源
     *
     * @param userId
     * @return
     */
    @DataSourceSlave
    public String query(long userId) {
        log.info("--slave--");
        return "user-" + userId;
    }

    /**
     * 使用master数据源
     *
     * @param userId
     */
    @DataSourceMaster
    public void remove(long userId) {
        log.info("--master--");
    }

}
