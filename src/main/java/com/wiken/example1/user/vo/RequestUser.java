package com.wiken.example1.user.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL) // null 인것은 반환될 때 포함되지 않고 출력
public class RequestUser {
    @NotNull
    @Size(min = 2, message = "이메일은 최소 2글자 이상을 적어주셔야 합니다.")
    @Email
    private String email;

    @NotNull
    @Size(min = 2, message = "이름은 최소 2글자 이상을 적어주셔야 합니다.")
    private String name;

    @NotNull
    @Size(min = 8, message = "비밀번호은 최소 8글자 이상을 적어주셔야 합니다.")
    private String pwd;

    @NotNull
    @Size(min = 8, message = "비밀번호은 최소 8글자 이상을 적어주셔야 합니다.")
    private String pwd1;
}
