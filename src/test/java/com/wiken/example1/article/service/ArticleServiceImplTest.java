package com.wiken.example1.article.service;

import com.wiken.example1.article.dto.ArticleDto;
import com.wiken.example1.user.entity.UserEntity;
import com.wiken.example1.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
class ArticleServiceImplTest {

//    @Autowired
//    private ArticleService articleService;
//    @Autowired
//    private UserRepository userRepository;

    @Test
    public void 게시글_생성() {

//        UserEntity userEntity = new UserEntity();
//        userEntity.setUserId(UUID.randomUUID().toString());
//        userEntity.setEmail("test@test.com");
//        userEntity.setEncryptedPwd("{np}1234");
//        userEntity.setName("test1");
//
//        userRepository.save(userEntity);
//
//        ArticleDto articleDto = new ArticleDto();
//        articleDto.setTitle("오늘 처음 게시글 올리는 사람입니다.");
//        articleDto.setContent("잘 부탁드립니다~");
//        articleDto.setUser(userEntity);
//
//        Assertions.assertNotNull(
//                articleService.createArticle(articleDto).getCreatedDate()
//        );
    }
}