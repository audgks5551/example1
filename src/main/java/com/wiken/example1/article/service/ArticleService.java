package com.wiken.example1.article.service;

import com.wiken.example1.article.dto.ArticleDto;
import com.wiken.example1.article.exception.ArticleNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ArticleService {
    ArticleDto createArticle(ArticleDto articleDto);
    ArticleDto findArticle(String articleId) throws ArticleNotFoundException;
    void deleteArticle(String articleId) throws ArticleNotFoundException;
    void modifyArticle(ArticleDto articleDto);
    Page<ArticleDto> findAllArticlesWithPage(int page, String search);
}
