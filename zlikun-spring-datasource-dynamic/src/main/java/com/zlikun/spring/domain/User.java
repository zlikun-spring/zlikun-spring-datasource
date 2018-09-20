package com.zlikun.spring.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author zlikun
 * @date 2018-09-20 11:34
 */
@Data
public class User {

    private Long id;
    private String username;
    private String password;
    private Date createTime;

}
