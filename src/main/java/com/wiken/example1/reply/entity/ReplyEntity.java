package com.wiken.example1.reply.entity;

import com.wiken.example1.reactionpoint.entity.eum.RelType;
import com.wiken.example1.user.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Any;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "replies")
public class ReplyEntity implements Serializable {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    /**
     * 글
     */
    @Setter
    @Column(nullable = false)
    private String content;

    /**
     * 글 UUID
     */
    @Setter
    @Column(name = "reply_id", nullable = false, unique = true)
    private String replyId;

    /**
     * 연관 id
     */
    @Column(nullable = false)
    private String relId;

    /**
     * 연관 타입
     */
    @Setter
    @Column(nullable = false)
    @Enumerated(STRING)
    private RelType relType;

    /**
     * 글쓴이 UUID
     */
    @Setter
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
    @ManyToOne(fetch = LAZY)
    private UserEntity user;
}
