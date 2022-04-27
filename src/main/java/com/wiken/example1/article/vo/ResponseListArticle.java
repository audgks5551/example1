package com.wiken.example1.article.vo;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.function.Function;

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
