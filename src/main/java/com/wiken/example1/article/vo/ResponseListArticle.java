package com.wiken.example1.article.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseListArticle {
    private String title;
    private String content;
    private String articleId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String userId;
    private String writer;
    private Integer goodReactionPoint;
    private Integer badReactionPoint;
}
