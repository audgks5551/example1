package com.wiken.example1.article.repository;

import com.wiken.example1.article.entity.ArticleEntity;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface ArticleRepository extends CrudRepository<ArticleEntity, Long> {
    ArticleEntity findByArticleId(String articleId);

    @Transactional
    void deleteByArticleId(String articleId);
}
