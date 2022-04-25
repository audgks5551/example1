package com.wiken.example1.reactionpoint.querydsl;

import com.wiken.example1.article.dto.ArticleDto;
import com.wiken.example1.article.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ReactionEntityTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void findArticleListWithReactionPointAll() {
        List<ArticleDto> articles =
                articleRepository.findArticleListWithReactionPointAll();

        articles.forEach(a -> {
            System.out.println("a.getArticleId() = " + a.getArticleId());
            System.out.println("a.getContent() = " + a.getContent());
            System.out.println("a.getWriter() = " + a.getWriter());
            System.out.println("a.getTitle() = " + a.getTitle());
            System.out.println("a.getUserId() = " + a.getUserId());
            System.out.println("a.getGoodReactionPoint() = " + a.getGoodReactionPoint());
            System.out.println("a.getBadReactionPoint() = " + a.getBadReactionPoint());
        });
    }

}
