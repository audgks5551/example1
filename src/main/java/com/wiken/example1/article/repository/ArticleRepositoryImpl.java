package com.wiken.example1.article.repository;

import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wiken.example1.article.dto.ArticleDto;
import com.wiken.example1.article.dto.QArticleDto;
import com.wiken.example1.reactionpoint.entity.eum.Point;
import com.wiken.example1.reactionpoint.entity.eum.RelType;
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
    public Page<ArticleDto> findArticleListWithReactionPointAndPageableAll(Pageable pageable, String search) {
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
                                reactionPointCase(GOOD),
                                reactionPointCase(BAD)
                        )
                )
                .from(articleEntity)
                .join(articleEntity.user, userEntity)
                .leftJoin(reactionPointEntity)
                .on(
                        compareId(reactionPointEntity.relId, articleEntity.articleId),
                        compareRelType(reactionPointEntity.relType)
                )
                .where(containsArticle(search))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(articleEntity.articleId)
                .fetch();

        /**
         * count
         */
        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(articleEntity.count())
                .where(containsArticle(search))
                .from(articleEntity);

        return PageableExecutionUtils.getPage(contentQuery, pageable, countQuery::fetchOne);
    }

    /**
     * article detail
     */
    @Override
    public ArticleDto findArticleWithReactionPoint(String articleId) {
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
                                reactionPointCase(GOOD),
                                reactionPointCase(BAD)
                        )
                )
                .from(articleEntity)
                .join(articleEntity.user, userEntity)
                .leftJoin(reactionPointEntity)
                .on(
                        compareId(reactionPointEntity.relId, articleEntity.articleId),
                        compareRelType(reactionPointEntity.relType)
                )
                .where(
                    articleEntity.articleId.eq(articleId)
                )
                .fetchOne();
    }

    /**
     * condition
     *  - relId1 == relId2
     */
    private BooleanExpression compareId(StringPath relId1, StringPath relId2) {
        return relId1.eq(relId2);
    }

    /**
     * condition
     *  - relType == ARTICLE
     */
    private BooleanExpression compareRelType(EnumPath<RelType> relType) {
        return relType.eq(ARTICLE);
    }

    /**
     * good reaction
     * bad reaction
     */
    private NumberExpression<Integer> reactionPointCase(Point point) {
        return new CaseBuilder()
                    .when(reactionPointEntity.point.eq(point))
                    .then(1)
                    .otherwise(0)
                    .sum()
                    .as(point.getValue() + "ReactionPoint");
    }

    /**
     * 검색
     */
    private BooleanExpression containsArticle(String search) {
        return search.trim().isEmpty() ? null :
                articleEntity.title.contains(search)
                        .or(articleEntity.content.contains(search))
                        .or(articleEntity.articleId.contains(search))
                        .or(articleEntity.user.name.contains(search));
    }
}
