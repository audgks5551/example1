package com.wiken.example1.reply.repository;

import com.wiken.example1.article.repository.ArticleRepositoryCustom;
import com.wiken.example1.reply.entity.ReplyEntity;
import org.springframework.data.repository.CrudRepository;

public interface ReplyRepository extends CrudRepository<ReplyEntity, Long>, ReplyRepositoryCustom {
}
