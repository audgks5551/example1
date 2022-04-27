package com.wiken.example1.article.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wiken.example1.article.dto.ArticleDto;
import com.wiken.example1.article.dto.QArticleDto;
import com.wiken.example1.reactionpoint.entity.eum.Point;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.wiken.example1.article.entity.QArticleEntity.articleEntity;
import static com.wiken.example1.reactionpoint.entity.eum.Point.*;
import static com.wiken.example1.reactionpoint.entity.eum.RelType.ARTICLE;
import static com.wiken.example1.reactionpoint.entity.QReactionPointEntity.reactionPointEntity;
import static com.wiken.example1.user.entity.QUserEntity.userEntity;

/**
 * Article QueryDsl
 */
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * article list
     *  - userEntity
     *  - reactionPointEntity
     */
    @Override
    public List<ArticleDto> findArticleListWithReactionPointAll() {

        return jpaQueryFactory
                .select(
                    new QArticleDto(
                            articleEntity.title,
                            articleEntity.content,
                            articleEntity.articleId,
                            articleEntity.createdDate,
                            articleEntity.modifiedDate,
                            userEntity.userId,
                            userEntity.name.as("writer"),
                            getReactionPoint(GOOD),
                            getReactionPoint(BAD)
                        )
                )
                .from(articleEntity)
                .join(articleEntity.user, userEntity)
                .leftJoin(reactionPointEntity)
                .on(getCondition())

                .groupBy(articleEntity.articleId)
                .fetch();
    }

    @Override
    public Page<ArticleDto> findArticleListWithReactionPointAndPageableAll(Pageable pageable) {
        /**
         * content
         */
        List<ArticleDto> contentQuery = jpaQueryFactory
                .select(
                        new QArticleDto(
                                articleEntity.title,
                                articleEntity.content,
                                articleEntity.articleId,
                                articleEntity.createdDate,
                                articleEntity.modifiedDate,
                                userEntity.userId,
                                userEntity.name.as("writer"),
                                getReactionPoint(GOOD),
                                getReactionPoint(BAD)
                        )
                )
                .from(articleEntity)
                .join(articleEntity.user, userEntity)
                .leftJoin(reactionPointEntity)
                .on(getCondition())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(articleEntity.articleId)
                .fetch();

        /**
         * count
         */
        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(articleEntity.count())
                .from(articleEntity)
                .join(articleEntity.user, userEntity)
                .leftJoin(reactionPointEntity)
                .on(getCondition());

        return PageableExecutionUtils.getPage(contentQuery, pageable, countQuery::fetchOne);
    }

    /**
     * condition
     *  1. reactionPointEntity.relId == articleEntity.articleId
     *  2. reactionPointEntity.relType == ARTICLE
     */
    private BooleanExpression getCondition() {
        return reactionPointEntity.relId.eq(articleEntity.articleId)
                .and(reactionPointEntity.relType.eq(ARTICLE));
    }



    /**
     * good reaction
     * bad reaction
     */
    private NumberExpression<Integer> getReactionPoint(Point point) {
        return new CaseBuilder()
                    .when(reactionPointEntity.point.eq(point))
                    .then(1)
                    .otherwise(0)
                    .sum()
                    .as(point.getValue() + "ReactionPoint");
    }
}
