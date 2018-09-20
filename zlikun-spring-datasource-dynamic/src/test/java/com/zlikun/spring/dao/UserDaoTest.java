package com.zlikun.spring.dao;

import com.zlikun.spring.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 由于测试目标库未做主从，所以可以明显看到只有master库里被插入了新数据，slave库没有
 *
 * @author zlikun
 * @date 2018-09-19 21:03
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    void get() {
        User user = userDao.get(1L);
        assertNotNull(user);
        assertEquals("zlikun", user.getUsername());
    }

    @Test
    void save() {
        User user = new User();
        user.setUsername("Peter");
        user.setPassword("123456");
        user.setCreateTime(new Date());
        Long userId = userDao.save(user);
        assertNotNull(userId);
    }
}