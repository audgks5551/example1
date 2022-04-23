package com.wiken.example1.article.entity;

import com.wiken.example1.article.entity.eum.RelType;
import com.wiken.example1.article.entity.eum.Point;
import com.wiken.example1.article.converter.PointConverter;
import com.wiken.example1.base.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

/**
 * 좋아요 entity
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "reaction_points")
public class ReactionPointEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String relId;

    @Column(nullable = false)
    @Enumerated(STRING)
    private RelType relType;

    @Column(nullable = false)
    @Convert(converter = PointConverter.class)
    private Point point;
}