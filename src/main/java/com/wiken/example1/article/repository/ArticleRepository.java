package com.wiken.example1.article.repository;

import com.wiken.example1.article.entity.ArticleEntity;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<ArticleEntity, Long> {
    ArticleEntity findByArticleId(String articleId);
}
