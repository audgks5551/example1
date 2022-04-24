package com.wiken.example1.article.repository;

import com.wiken.example1.article.entity.ArticleEntity;

import java.util.List;

public interface ArticleRepositoryCustom {
    List<ArticleEntity> findArticleListAll();
}
