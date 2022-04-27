package com.wiken.example1.article.repository;

import com.wiken.example1.article.dto.ArticleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleRepositoryCustom {
    List<ArticleDto> findArticleListWithReactionPointAll();
    Page<ArticleDto> findArticleListWithReactionPointAndPageableAll(Pageable pageable);
}
