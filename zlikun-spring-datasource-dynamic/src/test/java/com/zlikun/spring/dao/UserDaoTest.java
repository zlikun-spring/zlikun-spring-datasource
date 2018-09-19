package com.zlikun.spring.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author zlikun
 * @date 2018-09-19 21:03
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    void query() {
        userDao.query(1L);
    }

    @Test
    void remove() {
        userDao.remove(1L);
    }
}