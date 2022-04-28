package com.wiken.example1.reply.repository;

import com.wiken.example1.reactionpoint.entity.eum.RelType;
import com.wiken.example1.reply.dto.ReplyDto;

import java.util.List;

public interface ReplyRepositoryCustom {
    List<ReplyDto> findReplyListWithReactionPoint(String relId, RelType relType);
}
