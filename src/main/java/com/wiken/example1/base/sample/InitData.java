package com.wiken.example1.base.sample;

import com.wiken.example1.article.entity.ArticleEntity;
import com.wiken.example1.article.repository.ArticleRepository;
import com.wiken.example1.reactionpoint.entity.eum.RelType;
import com.wiken.example1.reply.entity.ReplyEntity;
import com.wiken.example1.user.entity.UserEntity;
import com.wiken.example1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.wiken.example1.reactionpoint.entity.eum.RelType.ARTICLE;

/**
 * 샘플 데이터 넣기
 */
@Profile("local")
@Component
public class InitData {

    private final InitMemberService initMemberService;


    public InitData(InitMemberService initMemberService) {
        this.initMemberService = initMemberService;
    }

    @PostConstruct
    public void init() {
        initMemberService.init();
    }

    @Component
    @RequiredArgsConstructor
    static class InitMemberService {

        @PersistenceContext
        private EntityManager em;
        private final BCryptPasswordEncoder passwordEncoder;
        private final ArticleRepository articleRepository;
        private final UserRepository userRepository;

        @Transactional
        public void init() {
            String password = "pass";

            for (int i=0 ; i < 10; i++) {
                /**
                 * user sample data
                 */
                UserEntity userEntity = new UserEntity();
                userEntity.setUserId(UUID.randomUUID().toString());
                userEntity.setEmail(String.format("test%d@test.com", i+1));
                userEntity.setEncryptedPwd(passwordEncoder.encode(password));
                userEntity.setName(String.format("test%d", i+1));
                em.persist(userEntity);

                /**
                 * article sample data
                 */
                for (int j=0; j < 10; j++) {
                    ArticleEntity articleEntity = new ArticleEntity();
                    articleEntity.setUser(userEntity);
                    articleEntity.setArticleId(UUID.randomUUID().toString());
                    articleEntity.setTitle(String.format("title%d", j+1));
                    articleEntity.setContent(String.format("content%d", j+1));
                    em.persist(articleEntity);
                }
            }

            /**
             * reply sample data
             */
            List<UserEntity> users = userRepository.findAll();
            Iterable<ArticleEntity> articles = articleRepository.findAll();
            Random random = new Random();
            articles.forEach(article -> {
                for(int i=0; i < 5; i++) {
                    ReplyEntity replyEntity = new ReplyEntity();
                    replyEntity.setReplyId(UUID.randomUUID().toString());
                    replyEntity.setRelId(article.getArticleId());
                    replyEntity.setRelType(ARTICLE);
                    replyEntity.setContent(String.format("reply%d", i));
                    replyEntity.setUser(
                            users.get(random.nextInt(users.size()))
                    );
                    em.persist(replyEntity);
                }
            });

        }
    }
}
