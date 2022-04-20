package com.wiken.example1.user.entity;

import com.wiken.example1.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

/**
 * 유저 Entity
 */
@Getter
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return encryptedPwd;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                ", encryptedPwd='" + "[PROTECTED]" + '\'' +
                '}';
    }
}