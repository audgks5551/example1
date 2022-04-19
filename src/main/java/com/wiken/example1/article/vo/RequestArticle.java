package com.wiken.example1.article.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL) // null 인것은 반환될 때 포함되지 않고 출력
public class RequestArticle {
    @NotNull
    @Size(min = 30, message = "이메일은 최소 2글자 이상을 적어주셔야 합니다.")
    private String title;

    @NotNull
    private String content;
}
