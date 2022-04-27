package com.wiken.example1.article.entity;

import com.wiken.example1.base.entity.BaseEntity;
import com.wiken.example1.reactionpoint.entity.ReactionPointEntity;
import com.wiken.example1.reply.entity.ReplyEntity;
import com.wiken.example1.user.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * article entity
 */
@Getter
@Entity
@Table(name = "articles")
public class ArticleEntity extends BaseEntity implements Serializable {
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
    @Column(name = "article_id", nullable = false, unique = true)
    private String articleId;

    /**
     * 글쓴이 UUID
     */
    @Setter
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
    @ManyToOne(fetch = LAZY)
    private UserEntity user;
}
