package com.wiken.example1.reply.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.wiken.example1.reactionpoint.entity.eum.RelType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyDto {
    private String content;
    private LocalDateTime createdDate;
    private String replyId;
    private String relId;
    private RelType relType;
    private String userId;
    private String writer;
    private String compareCurrentAndPastDates;

    @QueryProjection
    public ReplyDto(String content, LocalDateTime createdDate, String writer) {
        this.content = content;
        this.createdDate = createdDate;
        this.writer = writer;
    }
}



