package com.wiken.example1.article.dto;

import com.wiken.example1.user.entity.UserEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleDto {
    private String title;
    private String content;
    private String articleId;
    private UserEntity user;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
