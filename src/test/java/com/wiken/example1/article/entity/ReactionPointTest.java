package com.wiken.example1.article.entity;

import com.wiken.example1.article.repository.ReactionPointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static com.wiken.example1.article.entity.eum.RelType.ARTICLE;
import static com.wiken.example1.article.entity.eum.Point.GOOD;

@SpringBootTest
public class ReactionPointTest {

    @Autowired
    private ReactionPointRepository reactionPointRepository;

    @BeforeEach
    @DisplayName("저장 테스트")
    public void save() {
        ReactionPointEntity reactionPoint = new ReactionPointEntity();
        reactionPoint.setPoint(GOOD);
        reactionPoint.setRelId("RELID-UUID");
        reactionPoint.setRelType(ARTICLE);

        ReactionPointEntity savedReactionPoint = reactionPointRepository.save(reactionPoint);
        System.out.println("savedReactionPoint.getId() = " + savedReactionPoint.getId());
        System.out.println("savedReactionPoint.getPoint() = " + savedReactionPoint.getPoint());
        System.out.println("savedReactionPoint.getRelId() = " + savedReactionPoint.getRelId());
        System.out.println("savedReactionPoint.getArticleType() = " + savedReactionPoint.getRelType());
    }
}
