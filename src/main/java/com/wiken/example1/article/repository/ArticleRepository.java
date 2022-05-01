package com.wiken.example1.article.repository;

import com.wiken.example1.article.entity.ArticleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ArticleRepository extends CrudRepository<ArticleEntity, Long>, ArticleRepositoryCustom {
    ArticleEntity findByArticleId(String articleId);

    List<ArticleEntity> findAll();

    @Query("select a from ArticleEntity a join fetch a.user u")
    Iterable<ArticleEntity> findArticleWithUserAll();

    @Transactional
    void deleteByArticleId(String articleId);
}
