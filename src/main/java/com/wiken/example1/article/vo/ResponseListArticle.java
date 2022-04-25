package com.wiken.example1.article.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

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
