package com.wiken.example1.reply.repository;

import com.wiken.example1.reactionpoint.entity.eum.RelType;
import com.wiken.example1.reply.dto.ReplyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReplyRepositoryCustom {
    Page<ReplyDto> findReplyListWithReactionPoint(String relId, RelType relType, Pageable pageable);
}
