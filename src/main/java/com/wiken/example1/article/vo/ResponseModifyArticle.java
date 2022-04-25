package com.wiken.example1.article.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wiken.example1.user.entity.UserEntity;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL) // null 인것은 반환될 때 포함되지 않고 출력
public class ResponseModifyArticle {
    private String title;
    private String content;
    private String articleId;
    private String userId;
    private String writer;
}
