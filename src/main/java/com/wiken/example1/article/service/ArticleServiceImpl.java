package com.wiken.example1.article.service;

import com.wiken.example1.article.dto.ArticleDto;
import com.wiken.example1.article.entity.ArticleEntity;
import com.wiken.example1.article.exception.ArticleNotFoundException;
import com.wiken.example1.article.repository.ArticleRepository;
import com.wiken.example1.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper mapper;

    @Override
    public ArticleDto createArticle(ArticleDto articleDto) {
        ArticleEntity articleEntity = mapper.map(articleDto, ArticleEntity.class);
        articleEntity.setArticleId(UUID.randomUUID().toString());

        ArticleEntity savedArticle = articleRepository.save(articleEntity);

        ArticleDto returnValue = mapper.map(savedArticle, ArticleDto.class);

        return returnValue;
    }

    @Override
    public Iterable<ArticleEntity> findAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public ArticleEntity findArticle(String articleId) throws ArticleNotFoundException {
        ArticleEntity articleEntity = articleRepository.findByArticleId(articleId);

        if (articleEntity == null) {
            throw new ArticleNotFoundException("게시글을 찾을 수 없습니다.");
        }

        return articleEntity;
    }

    @Override
    public void deleteArticle(String articleId) {
        articleRepository.deleteByArticleId(articleId);
    }
}
