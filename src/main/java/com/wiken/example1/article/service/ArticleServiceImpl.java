package com.wiken.example1.article.service;

import com.wiken.example1.article.dto.ArticleDto;
import com.wiken.example1.article.entity.ArticleEntity;
import com.wiken.example1.article.exception.ArticleNotFoundException;
import com.wiken.example1.article.repository.ArticleRepository;
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
    public Iterable<ArticleDto> findAllArticles() {
        return articleRepository.findArticleListWithReactionPointAll();
    }

    @Override
    public ArticleDto findArticle(String articleId) throws ArticleNotFoundException {
        ArticleEntity articleEntity = articleRepository.findByArticleId(articleId);

        if (articleEntity == null) {
            throw new ArticleNotFoundException("게시글을 찾을 수 없습니다.");
        }

        ArticleDto articleDto = mapper.map(articleEntity, ArticleDto.class);
        articleDto.setUserId(articleEntity.getUser().getUserId());
        articleDto.setWriter(articleEntity.getUser().getName());

        return articleDto;
    }

    @Override
    public void deleteArticle(String articleId) throws ArticleNotFoundException {

        try {
            articleRepository.deleteByArticleId(articleId);
        } catch (Exception e) {
            throw new ArticleNotFoundException("게시글을 삭제할 수 없습니다.");
        }
    }

    @Transactional
    @Override
    public void modifyArticle(ArticleDto articleDto) {

        ArticleEntity articleEntity = articleRepository.findByArticleId(articleDto.getArticleId());
        articleEntity.setTitle(articleDto.getTitle());
        articleEntity.setContent(articleDto.getContent());
    }
}
