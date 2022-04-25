package com.wiken.example1.article.service;

import com.wiken.example1.article.dto.ArticleDto;
import com.wiken.example1.article.exception.ArticleNotFoundException;

public interface ArticleService {
    ArticleDto createArticle(ArticleDto articleDto);
    Iterable<ArticleDto> findAllArticles();
    ArticleDto findArticle(String articleId) throws ArticleNotFoundException;
    void deleteArticle(String articleId) throws ArticleNotFoundException;
    void modifyArticle(ArticleDto articleDto);
}
