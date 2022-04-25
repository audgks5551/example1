package com.wiken.example1.article.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wiken.example1.article.entity.ArticleEntity;

import javax.persistence.EntityManager;

import java.util.List;

import static com.wiken.example1.article.entity.QArticleEntity.articleEntity;
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
}