package com.wiken.example1.article.vo;

import com.wiken.example1.user.entity.UserEntity;
import lombok.Data;

@Data
public class RequestModifyArticle {
    private String title;
    private String content;
    private String articleId;
    private UserEntity user;
}
