package com.wiken.example1.reply.service;

import com.wiken.example1.base.utils.DateUtils;
import com.wiken.example1.reactionpoint.entity.eum.RelType;
import com.wiken.example1.reply.dto.ReplyDto;
import com.wiken.example1.reply.entity.ReplyEntity;
import com.wiken.example1.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * reply service
 */
@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    /**
     * 답변 생성
     */
    @Override
    public void createReply(ReplyEntity replyEntity) {
        replyEntity.setReplyId(UUID.randomUUID().toString());
        replyRepository.save(replyEntity);
    }

    /**
     * relId와 relType을 통한 답변 조회
     */
    @Override
    public Iterable<ReplyDto> replyListWithUsername(String relId, RelType relType) {
        Iterable<ReplyDto> replyList = replyRepository.findReplyListWithReactionPoint(relId, relType);

        replyList.forEach(replyDto ->
            replyDto.setCompareCurrentAndPastDates(
                    DateUtils.getCompareCurrentAndPastDates(replyDto.getCreatedDate())
            )
        );

        return replyList;
    }
}
