package com.wiken.example1.reply.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wiken.example1.reactionpoint.entity.eum.RelType;
import com.wiken.example1.reply.dto.QReplyDto;
import com.wiken.example1.reply.dto.ReplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.wiken.example1.article.entity.QArticleEntity.articleEntity;
import static com.wiken.example1.reply.entity.QReplyEntity.replyEntity;
import static com.wiken.example1.user.entity.QUserEntity.userEntity;

/**
 * reply repository
 */
@RequiredArgsConstructor
public class ReplyRepositoryImpl implements ReplyRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ReplyDto> findReplyListWithReactionPoint(String relId, RelType relType, Pageable pageable) {
        /**
         * content
         */
        List<ReplyDto> contentQuery = jpaQueryFactory
                .select(
                        new QReplyDto(
                                replyEntity.content,
                                replyEntity.createdDate,
                                userEntity.name.as("writer")
                        )
                )
                .from(replyEntity)
                .join(replyEntity.user, userEntity)
                .where(
                        replyEntity.relId.eq(relId),
                        replyEntity.relType.eq(relType)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(replyEntity.createdDate.desc())
                .fetch();

        /**
         * count
         */
        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(replyEntity.count())
                .from(replyEntity)
                .join(replyEntity.user, userEntity)
                .where(
                        replyEntity.relId.eq(relId),
                        replyEntity.relType.eq(relType)
                );

        return PageableExecutionUtils.getPage(contentQuery, pageable, countQuery::fetchOne);
    }
}
