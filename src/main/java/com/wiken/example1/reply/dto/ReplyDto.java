package com.wiken.example1.reply.dto;

import com.wiken.example1.reactionpoint.entity.eum.RelType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyDto {
    private String content;
    private String replyId;
    private String relId;
    private RelType relType;
    private String userId;
}
