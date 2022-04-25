package com.wiken.example1.article.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.wiken.example1.user.entity.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private String title;
    private String content;
    private String articleId;
    private UserEntity user;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @QueryProjection
    public ArticleDto(String title, String content, String articleId, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.title = title;
        this.content = content;
        this.articleId = articleId;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
