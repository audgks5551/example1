package com.wiken.example1.article.entity;

import com.wiken.example1.article.repository.ReactionPointRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.wiken.example1.article.entity.eum.RelType.ARTICLE;
import static com.wiken.example1.article.entity.eum.Point.GOOD;

@SpringBootTest
public class ReactionPointTest {

    @Autowired
    private ReactionPointRepository reactionPointRepository;

    @Test
    @DisplayName("저장 테스트")
    public void save() {
        ReactionPointEntity reactionPoint = new ReactionPointEntity();
        reactionPoint.setPoint(GOOD);
        reactionPoint.setRelId("RELID-UUID");
        reactionPoint.setRelType(ARTICLE);

        ReactionPointEntity savedReactionPoint = reactionPointRepository.save(reactionPoint);

        Assertions.assertThat(savedReactionPoint.getCreatedDate()).isNotNull();

        System.out.println("savedReactionPoint.getId() = " + savedReactionPoint.getId());
        System.out.println("savedReactionPoint.getPoint() = " + savedReactionPoint.getPoint());
        System.out.println("savedReactionPoint.getRelId() = " + savedReactionPoint.getRelId());
        System.out.println("savedReactionPoint.getArticleType() = " + savedReactionPoint.getRelType());
    }
}
