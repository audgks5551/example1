package com.wiken.example1.reactionpoint.repository;

import com.wiken.example1.article.entity.eum.RelType;
import com.wiken.example1.reactionpoint.entity.ReactionPointEntity;
import com.wiken.example1.user.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReactionPointRepository extends CrudRepository<ReactionPointEntity, Long> {
    Optional<ReactionPointEntity> findByRelIdAndRelTypeAndUser (
            String relId, RelType relType, UserEntity user);
}
