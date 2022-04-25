package com.wiken.example1.reactionpoint.service;

import com.wiken.example1.reactionpoint.dto.ReactionPointDto;
import com.wiken.example1.reactionpoint.entity.ReactionPointEntity;
import com.wiken.example1.reactionpoint.exception.PointNotFoundException;
import com.wiken.example1.reactionpoint.exception.ReactionPointEntityNotFoundException;
import com.wiken.example1.reactionpoint.repository.ReactionPointRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.wiken.example1.reactionpoint.entity.eum.Point.BAD;
import static com.wiken.example1.reactionpoint.entity.eum.Point.GOOD;

@Service
@RequiredArgsConstructor
public class ReactionPointServiceImpl implements ReactionPointService {

    private final ReactionPointRepository reactionPointRepository;
    private final ModelMapper mapper;

    /**
     * reactionPoint 생성
     */
    @Override
    public void createReactionPoint(ReactionPointDto reactionPointDto) {

        ReactionPointEntity reactionPointEntity =
                mapper.map(reactionPointDto, ReactionPointEntity.class);

        reactionPointRepository.save(reactionPointEntity);
    }

    /**
     * 유저가 좋아요와 싫어요를 가지고 있는지 확인
     *  - 없으면 null 반환
     */
    @Override
    public ReactionPointDto findReactionPoint(ReactionPointDto reactionPointDto) {


        ReactionPointEntity reactionPointEntity =
                reactionPointRepository.findByRelIdAndRelTypeAndUser(
                    reactionPointDto.getRelId(),
                    reactionPointDto.getRelType(),
                    reactionPointDto.getUser()
                )
                        .orElse(null);

        /**
         * 없으면 널 반환
         */
        if (reactionPointEntity == null) {
            return null;
        }

        ReactionPointDto findReactionPointDto = mapper.map(reactionPointEntity, ReactionPointDto.class);

        return findReactionPointDto;
    }

    /**
     * 좋아요 또는 싫어요 취소
     */
    @Override
    public void cancelReactionPointById(Long reactionPointId) {
        reactionPointRepository.deleteById(reactionPointId);
    }

    /**
     * Point 변경
     *  - GOOD -> BAD
     *  - BAD -> GOOD
     */
    @Transactional
    @Override
    public void changeReactionPoint(ReactionPointDto reactionPointDto)
            throws ReactionPointEntityNotFoundException, PointNotFoundException {

        ReactionPointEntity reactionPointEntity =
                reactionPointRepository.findById(reactionPointDto.getId())
                    .orElse(null);

        if (reactionPointEntity == null) {
            throw new ReactionPointEntityNotFoundException("reactionPointEntity가 존재하지 않습니다.");
        }

        if (reactionPointDto.getPoint() == GOOD) {
            reactionPointEntity.setPoint(BAD);
        }
        else if (reactionPointDto.getPoint() == BAD) {
            reactionPointEntity.setPoint(GOOD);
        }
        else {
            throw new PointNotFoundException("일치하는 Point 타입이 존재하지 않습니다.");
        }
    }
}
