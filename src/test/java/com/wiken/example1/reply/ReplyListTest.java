package com.wiken.example1.reply;

import com.wiken.example1.reactionpoint.entity.eum.RelType;
import com.wiken.example1.reply.dto.ReplyDto;
import com.wiken.example1.reply.repository.ReplyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootTest
public class ReplyListTest {

    @Autowired
    private ReplyRepository repository;

    /**
     * 현재 날짜를 기준으로 날짜 비교
     */
    @Test
    public void reply_date() {
        String relId = "00ae9be2-3b6b-49fa-907e-122be7dcae0a";
        RelType relType = RelType.ARTICLE;
        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        PageRequest pageable = PageRequest.of(2, 2);
        List<ReplyDto> replyDtos = repository.findReplyListWithReactionPoint(relId, relType, pageable);
        String result = null;
        for (ReplyDto replyDto : replyDtos) {
            long YEARS = ChronoUnit.YEARS.between(replyDto.getCreatedDate(), currentLocalDateTime);
            long MONTHS = ChronoUnit.MONTHS.between(replyDto.getCreatedDate(), currentLocalDateTime);
            long WEEKS = ChronoUnit.WEEKS.between(replyDto.getCreatedDate(), currentLocalDateTime);
            long DAYS = ChronoUnit.DAYS.between(replyDto.getCreatedDate(), currentLocalDateTime);
            long HOURS = ChronoUnit.HOURS.between(replyDto.getCreatedDate(), currentLocalDateTime);
            long SECONDS = ChronoUnit.SECONDS.between(replyDto.getCreatedDate(), currentLocalDateTime);

            if (YEARS != 0) {
                result = String.format("%d년 전", YEARS);
            } else if (MONTHS != 0) {
                result = String.format("%d달 전", MONTHS);
            } else if (WEEKS != 0) {
                result = String.format("%d주 전", WEEKS);
            } else if (DAYS != 0) {
                result = String.format("%d일 전", DAYS);
            } else if (HOURS != 0) {
                result = String.format("%d시간 전", HOURS);
            } else if (SECONDS != 0) {
                result = String.format("%d분 전", SECONDS/60);
            } else {
                result = "방금 전";
            }

            System.out.println("result = " + result);
        }
    }
}
