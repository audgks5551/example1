package com.wiken.example1.article.querydsl;

import com.wiken.example1.article.dto.ArticleDto;
import com.wiken.example1.article.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ArticleListTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void article_list() {
        List<ArticleDto> articles = articleRepository.findArticleListWithReactionPointAll();
        for (ArticleDto article : articles) {
            System.out.println("article.getArticleId() = " + article.getArticleId());
        }
    }
}
