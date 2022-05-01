package com.wiken.example1.reply.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseReply {
    private String writer;
    private String content;
    private String compareCurrentAndPastDates;
}
