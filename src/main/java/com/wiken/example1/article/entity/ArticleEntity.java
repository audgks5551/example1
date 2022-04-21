package com.wiken.example1.article.entity;

import com.wiken.example1.base.entity.BaseEntity;
import com.wiken.example1.user.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * article entity
 */
@Getter
@Entity
@Table(name = "articles")
public class ArticleEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    /**
     * 제목
     */
    @Setter
    @Column(nullable = false)
    private String title;

    /**
     * 글
     */
    @Setter
    @Column(nullable = false)
    private String content;

    /**
     * 글 UUID
     */
    @Setter
    @Column(nullable = false, unique = true)
    private String articleId;

    /**
     * 글쓴이 UUID
     */
    @Setter
    @Column(nullable = false)
    private String userId;
}
