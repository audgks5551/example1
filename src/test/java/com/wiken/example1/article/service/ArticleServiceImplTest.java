package com.wiken.example1.article.service;

import com.wiken.example1.article.dto.ArticleDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ArticleServiceImplTest {

    @Autowired
    private ArticleService articleService;

    @Test
    public void 게시글_생성() {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setTitle("오늘 처음 게시글 올리는 사람입니다.");
        articleDto.setContent("잘 부탁드립니다~");
//        articleDto.setUserId("USER-UUID1");

        Assertions.assertNotNull(
                articleService.createArticle(articleDto).getCreatedDate()
        );
    }
}