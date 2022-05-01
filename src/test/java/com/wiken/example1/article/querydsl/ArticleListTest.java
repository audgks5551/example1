package com.wiken.example1.article.querydsl;

import com.wiken.example1.article.dto.ArticleDto;
import com.wiken.example1.article.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@SpringBootTest
public class ArticleListTest {

    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private ModelMapper mapper;

    @Test
    public void article_list_with_page() {
        int page = 0;
        PageRequest pageable = PageRequest.of(0, 10);
        Page<ArticleDto> articles = articleRepository.findArticleListWithReactionPointAndPageableAll(pageable, "");

        System.out.println("articles.getTotalPages() = " + articles.getTotalPages());
        System.out.println("articles.getContent() = " + articles.getContent());
        System.out.println("articles.getSize() = " + articles.getSize());
        System.out.println("articles.getSort() = " + articles.getSort());
        System.out.println("articles.getTotalElements() = " + articles.getTotalElements());
        System.out.println("articles.getNumberOfElements() = " + articles.getNumberOfElements());

    }
}
