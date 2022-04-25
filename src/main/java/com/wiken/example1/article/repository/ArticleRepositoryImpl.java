package com.wiken.example1.article.repository;

import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wiken.example1.article.dto.ArticleDto;
import com.wiken.example1.article.dto.QArticleDto;
import com.wiken.example1.article.entity.ArticleEntity;
import com.wiken.example1.article.entity.eum.Point;
import com.wiken.example1.article.entity.eum.RelType;
import com.wiken.example1.reactionpoint.entity.QReactionPointEntity;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static com.wiken.example1.article.entity.QArticleEntity.articleEntity;
import static com.wiken.example1.article.entity.eum.Point.*;
import static com.wiken.example1.article.entity.eum.RelType.ARTICLE;
import static com.wiken.example1.reactionpoint.entity.QReactionPointEntity.reactionPointEntity;
import static com.wiken.example1.user.entity.QUserEntity.userEntity;

public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public ArticleRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<ArticleEntity> findArticleListAll() {
        return jpaQueryFactory
                .selectFrom(articleEntity)
                .join(articleEntity.user, userEntity).fetchJoin()
                .fetch();
    }

    @Transactional
    @Override
    public List<ArticleDto> findArticleListWithReactionPointAll() {

        NumberExpression<Integer> goodReactionPoint = new CaseBuilder()
                .when(reactionPointEntity.point.eq(GOOD))
                .then(1)
                .otherwise(0)
                .sum()
                .as("goodReactionPoint");

        NumberExpression<Integer> badReactionPoint = new CaseBuilder()
                .when(reactionPointEntity.point.eq(BAD))
                .then(1)
                .otherwise(0)
                .sum()
                .as("goodReactionPoint");

        List<ArticleDto> articleDtos = jpaQueryFactory
                .select(
                        new QArticleDto(
                                articleEntity.title,
                                articleEntity.content,
                                articleEntity.articleId,
                                articleEntity.createdDate,
                                articleEntity.modifiedDate,
                                userEntity.userId,
                                userEntity.name.as("writer"),
                                goodReactionPoint,
                                badReactionPoint
                        )
                )
                .from(articleEntity)
                .join(articleEntity.user, userEntity)
                .leftJoin(reactionPointEntity).on(
                        reactionPointEntity.relId.eq(articleEntity.articleId)
                                .and(reactionPointEntity.relType.eq(ARTICLE)))
                .groupBy(articleEntity.articleId)
                .fetch();
        return articleDtos;
    }
}
