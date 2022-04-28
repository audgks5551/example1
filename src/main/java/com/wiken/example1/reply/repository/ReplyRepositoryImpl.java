package com.wiken.example1.reply.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wiken.example1.reactionpoint.entity.eum.RelType;
import com.wiken.example1.reply.dto.QReplyDto;
import com.wiken.example1.reply.dto.ReplyDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.wiken.example1.reply.entity.QReplyEntity.replyEntity;
import static com.wiken.example1.user.entity.QUserEntity.userEntity;

/**
 * reply repository
 */
@RequiredArgsConstructor
public class ReplyRepositoryImpl implements ReplyRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ReplyDto> replyListWithReactionPoint(String relId, RelType relType) {

        return jpaQueryFactory
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
                .orderBy(replyEntity.createdDate.desc())
                .fetch();
    }
}
