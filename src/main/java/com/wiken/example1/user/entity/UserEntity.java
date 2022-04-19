package com.wiken.example1.user.entity;

import com.wiken.example1.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 유저 Entity
 */
@Getter
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

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
}