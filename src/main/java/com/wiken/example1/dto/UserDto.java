package com.wiken.example1.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {

    /**
     * 공통 부분
     */
    private Long id;
    private String email;
    private String name;
    private String userId;

    /**
     * vo
     */
    private String pwd;

    /**
     * entity
     */
    private String encryptedPwd;
    private Date createdAt;
}
