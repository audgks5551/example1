package com.wiken.example1.article.service;

import com.wiken.example1.article.dto.ArticleDto;
import com.wiken.example1.article.entity.ArticleEntity;
import com.wiken.example1.article.exception.ArticleNotFoundException;

public interface ArticleService {
    ArticleDto createArticle(ArticleDto articleDto);
    Iterable<ArticleEntity> findAllArticles();
    ArticleEntity findArticle(String articleId) throws ArticleNotFoundException;

}
