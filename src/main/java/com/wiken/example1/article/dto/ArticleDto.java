package com.wiken.example1.article.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleDto {
    private String title;
    private String content;
    private String articleId;
    private String userId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
