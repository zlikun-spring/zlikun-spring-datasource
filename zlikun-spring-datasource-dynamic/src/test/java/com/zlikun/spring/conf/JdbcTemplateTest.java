package com.zlikun.spring.conf;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author zlikun
 * @date 2018-09-19 19:49
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class JdbcTemplateTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void test() {
        assertEquals("1", jdbcTemplate.queryForObject("SELECT 1 FROM DUAL", String.class));
    }

}
