package com.zlikun.spring.dao;

import com.zlikun.spring.domain.User;
import com.zlikun.spring.ds.DataSourceMaster;
import com.zlikun.spring.ds.DataSourceSlave;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;

import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * @author zlikun
 * @date 2018-09-19 21:01
 */
@Slf4j
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询用户，使用slave数据源
     *
     * @param userId
     * @return
     */
    @DataSourceSlave
    public User get(long userId) {
        log.info("--slave--");
        return jdbcTemplate.queryForObject("SELECT * FROM `tbl_user` WHERE `id` = ?",
                new BeanPropertyRowMapper<>(User.class), userId);
    }

    /**
     * 保存用户，使用master数据源
     *
     * @param user
     * @return
     */
    @DataSourceMaster
    public Long save(User user) {
        log.info("--master--");
        String sql = "INSERT INTO `tbl_user` (`username`, `password`, `create_time`) VALUES (?, ?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            ps.setObject(3, user.getCreateTime());
            return ps;
        }, holder);
        return holder.getKey().longValue();
    }

}
