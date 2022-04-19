package com.wiken.example1.article.service;

import com.wiken.example1.article.dto.ArticleDto;
import com.wiken.example1.article.entity.ArticleEntity;
import com.wiken.example1.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper mapper;

    @Override
    public void createArticle(ArticleDto articleDto) {
        ArticleEntity articleEntity = mapper.map(articleDto, ArticleEntity.class);
        articleEntity.setArticleId(UUID.randomUUID().toString());

        articleRepository.save(articleEntity);
    }

    @Override
    public Iterable<ArticleEntity> findAllArticles() {
        return articleRepository.findAll();
    }
}
