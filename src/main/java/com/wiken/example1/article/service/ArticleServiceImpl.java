package com.wiken.example1.article.service;

import com.wiken.example1.article.dto.ArticleDto;
import com.wiken.example1.article.entity.ArticleEntity;
import com.wiken.example1.article.exception.ArticleNotFoundException;
import com.wiken.example1.article.repository.ArticleRepository;
import com.wiken.example1.base.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static com.wiken.example1.article.entity.QArticleEntity.articleEntity;

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
    public ArticleDto findArticle(String articleId) throws ArticleNotFoundException {
        ArticleDto articleDto = articleRepository.findArticleWithReactionPoint(articleId);

        System.out.println("articleDto == null = " + articleDto == null);

        if (articleDto == null) {
            throw new ArticleNotFoundException("게시글을 찾을 수 없습니다.");
        }

        articleDto.setCompareCurrentAndPastDates(
                DateUtils.getCompareCurrentAndPastDates(articleDto.getCreatedDate())
        );

        articleDto.setOrderlyDate(
                DateUtils.getOrderlyDate(articleDto.getCreatedDate())
        );

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

    @Override
    public Page<ArticleDto> findAllArticlesWithPage(int page) {
        PageRequest pageable = PageRequest.of(page, 10);
        return articleRepository.findArticleListWithReactionPointAndPageableAll(pageable);
    }
}
