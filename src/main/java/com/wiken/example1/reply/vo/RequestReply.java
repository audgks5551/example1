package com.wiken.example1.reply.vo;

import com.wiken.example1.reactionpoint.entity.eum.RelType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RequestReply {
    @NotNull
    private String content;
    @NotNull
    private String relId;
    @NotNull
    private RelType relType;
    @NotNull
    private String redirectUri;
}
