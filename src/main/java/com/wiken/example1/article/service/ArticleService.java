package com.wiken.example1.article.service;

import com.wiken.example1.article.dto.ArticleDto;
import com.wiken.example1.article.entity.ArticleEntity;

public interface ArticleService {
    void createArticle(ArticleDto articleDto);
    Iterable<ArticleEntity> findAllArticles();
}
