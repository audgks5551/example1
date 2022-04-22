package com.wiken.example1.article.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestCreateArticle {
    @NotNull
    @Size(min = 30, message = "이메일은 최소 2글자 이상을 적어주셔야 합니다.")
    private String title;

    @NotNull
    private String content;
}
