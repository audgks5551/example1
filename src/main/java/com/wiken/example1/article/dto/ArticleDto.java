package com.wiken.example1.article.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.wiken.example1.user.entity.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ArticleDto {
    /**
     * ArticleEntity
     */
    private String title;
    private String content;
    private String articleId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    /**
     * UserEntity
     */
    private String userId;
    private String writer;
    private UserEntity user;

    /**
     * ReactionPointEntity
     */
    private Integer goodReactionPoint;
    private Integer badReactionPoint;

    /**
     * util
     */
    private String compareCurrentAndPastDates;
    private String orderlyDate;

    /**
     * article list
     */
    @QueryProjection
    public ArticleDto(String title, String content, String articleId, LocalDateTime createdDate, LocalDateTime modifiedDate, String userId, String writer, Integer goodReactionPoint, Integer badReactionPoint) {
        this.title = title;
        this.content = content;
        this.articleId = articleId;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.userId = userId;
        this.writer = writer;
        this.goodReactionPoint = goodReactionPoint;
        this.badReactionPoint = badReactionPoint;
    }
}
