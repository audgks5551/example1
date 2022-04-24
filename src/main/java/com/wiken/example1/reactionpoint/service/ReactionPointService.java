package com.wiken.example1.reactionpoint.service;

import com.wiken.example1.reactionpoint.dto.ReactionPointDto;
import com.wiken.example1.reactionpoint.exception.PointNotFoundException;
import com.wiken.example1.reactionpoint.exception.ReactionPointEntityNotFoundException;

public interface ReactionPointService {
    void createReactionPoint(ReactionPointDto reactionPointDto);
    ReactionPointDto findReactionPoint(ReactionPointDto reactionPointDto);
    void cancelReactionPointById(Long reactionPointId);
    void changeReactionPoint(ReactionPointDto reactionPointDto) throws ReactionPointEntityNotFoundException, PointNotFoundException;
}
