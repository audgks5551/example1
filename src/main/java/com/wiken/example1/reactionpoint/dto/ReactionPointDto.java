package com.wiken.example1.reactionpoint.dto;

import com.wiken.example1.article.entity.eum.Point;
import com.wiken.example1.article.entity.eum.RelType;
import com.wiken.example1.user.entity.UserEntity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReactionPointDto {
    private Long id;
    private String relId;
    private RelType relType;
    private Point point;
    private UserEntity user;
}
