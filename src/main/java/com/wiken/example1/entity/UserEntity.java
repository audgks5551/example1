package com.wiken.example1.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

/**
 * 유저 Entity
 */
@Getter
@Entity
@Table(name = "users")
public class UserEntity {

    /**
     * 테이블 번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 이메일
     */
    @Setter
    @Column(nullable = false, length = 50, unique = true)
    private String email;

    /**
     * 이름
     */
    @Setter
    @Column(nullable = false, length = 50)
    private String name;

    /**
     * UUID를 통한 유저 번호
     */
    @Setter
    @Column(nullable = false, unique = true)
    private String userId;

    /**
     * 비밀번호
     */
    @Setter
    @Column(nullable = false, unique = true)
    private String encryptedPwd;

    /**
     * 가입 일자
     */
    @Setter
    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createdAt;
}