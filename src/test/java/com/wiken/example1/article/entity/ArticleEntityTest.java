package com.wiken.example1.article.entity;

import com.wiken.example1.article.repository.ArticleRepository;
import com.wiken.example1.user.entity.UserEntity;
import com.wiken.example1.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
class ArticleEntityTest {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void 게시판_생성() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setEmail("test@test.com");
        userEntity.setEncryptedPwd("{np}1234");
        userEntity.setName("test1");

        userRepository.save(userEntity);

        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle("오늘 처음 게시글 올리는 사람입니다.");
        articleEntity.setContent("잘 부탁드립니다~");
        articleEntity.setArticleId(UUID.randomUUID().toString());
        articleEntity.setUser(userEntity);

        ArticleEntity savedArticleEntity = articleRepository.save(articleEntity);

        Assertions.assertThat(savedArticleEntity.getCreatedDate()).isNotNull();

        System.out.println("savedArticleEntity.getArticleId() = " + savedArticleEntity.getArticleId());
        System.out.println("savedArticleEntity.getContent() = " + savedArticleEntity.getContent());
        System.out.println("savedArticleEntity.getTitle() = " + savedArticleEntity.getTitle());
        System.out.println("savedArticleEntity.getId() = " + savedArticleEntity.getId());
        System.out.println("savedArticleEntity.getCreatedDate() = " + savedArticleEntity.getCreatedDate());
        System.out.println("savedArticleEntity.getModifiedDate() = " + savedArticleEntity.getModifiedDate());
    }
}