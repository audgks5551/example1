package com.wiken.example1.article.entity;

import com.wiken.example1.article.repository.ArticleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ArticleEntityTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @Rollback(value = false)
    public void 게시판_생성() {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle("오늘 처음 게시글 올리는 사람입니다.");
        articleEntity.setContent("잘 부탁드립니다~");
        articleEntity.setArticleId("ARTICLE-UUID1");
//        articleEntity.setUser("USER-UUID1");

        Assertions.assertNotNull(
                articleRepository.save(articleEntity).getCreatedDate()
        );
    }

}