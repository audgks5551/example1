package com.wiken.example1.user.entity;

import com.wiken.example1.article.entity.ArticleEntity;
import com.wiken.example1.base.entity.BaseEntity;
import com.wiken.example1.reactionpoint.entity.ReactionPointEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * 유저 Entity
 */
@Getter
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity implements Serializable {

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
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    /**
     * 비밀번호
     */
    @Setter
    @Column(nullable = false, unique = true)
    private String encryptedPwd;
}