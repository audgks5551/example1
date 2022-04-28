package com.wiken.example1.reply.service;

import com.wiken.example1.reactionpoint.entity.eum.RelType;
import com.wiken.example1.reply.dto.ReplyDto;
import com.wiken.example1.reply.entity.ReplyEntity;
import com.wiken.example1.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ModelMapper mapper;
    private final ReplyRepository replyRepository;

    @Override
    public void createReply(ReplyEntity replyEntity) {
        replyEntity.setReplyId(UUID.randomUUID().toString());
        replyRepository.save(replyEntity);
    }

    @Override
    public List<ReplyDto> replyListWithUsername(String relId, RelType relType) {
        List<ReplyDto> replyList = replyRepository.findReplyListWithReactionPoint(relId, relType);
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        Stream<ReplyDto> ReplyDto = replyList.stream().map(replyDto -> {
            long YEARS = ChronoUnit.YEARS.between(replyDto.getCreatedDate(), currentLocalDateTime);
            long MONTHS = ChronoUnit.MONTHS.between(replyDto.getCreatedDate(), currentLocalDateTime);
            long WEEKS = ChronoUnit.WEEKS.between(replyDto.getCreatedDate(), currentLocalDateTime);
            long DAYS = ChronoUnit.DAYS.between(replyDto.getCreatedDate(), currentLocalDateTime);
            long HOURS = ChronoUnit.HOURS.between(replyDto.getCreatedDate(), currentLocalDateTime);
            long SECONDS = ChronoUnit.SECONDS.between(replyDto.getCreatedDate(), currentLocalDateTime);

            if (YEARS != 0) {
                replyDto.setCompareCurrentAndPastDates(String.format("%d년 전", YEARS));
            } else if (MONTHS != 0) {
                replyDto.setCompareCurrentAndPastDates(String.format("%d달 전", MONTHS));
            } else if (WEEKS != 0) {
                replyDto.setCompareCurrentAndPastDates(String.format("%d주 전", WEEKS));
            } else if (DAYS != 0) {
                replyDto.setCompareCurrentAndPastDates(String.format("%d일 전", DAYS));
            } else if (HOURS != 0) {
                replyDto.setCompareCurrentAndPastDates(String.format("%d시간 전", HOURS));
            } else if (SECONDS != 0) {
                replyDto.setCompareCurrentAndPastDates(String.format("%d분 전", SECONDS / 60));
            } else {
                replyDto.setCompareCurrentAndPastDates("방금 전");
            }

            return replyDto;
        });
        return replyList;
    }
}
