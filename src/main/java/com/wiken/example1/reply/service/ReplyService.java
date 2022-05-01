package com.wiken.example1.reply.service;

import com.wiken.example1.reactionpoint.entity.eum.RelType;
import com.wiken.example1.reply.dto.ReplyDto;
import com.wiken.example1.reply.entity.ReplyEntity;

public interface ReplyService {
    void createReply(ReplyEntity replyEntity);
    Iterable<ReplyDto> replyListWithUsername(String relId, RelType relType);
}
