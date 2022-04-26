package com.wiken.example1.reply.service;

import com.wiken.example1.reply.entity.ReplyEntity;
import com.wiken.example1.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ModelMapper mapper;
    private final ReplyRepository replyRepository;

    @Override
    public void createReply(ReplyEntity replyEntity) {
        replyEntity.setReplyId(UUID.randomUUID().toString());
        replyRepository.save(replyEntity);
    }
}
