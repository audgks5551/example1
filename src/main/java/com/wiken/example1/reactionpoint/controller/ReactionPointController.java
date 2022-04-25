package com.wiken.example1.reactionpoint.controller;

import com.wiken.example1.reactionpoint.entity.eum.Point;
import com.wiken.example1.reactionpoint.entity.eum.RelType;
import com.wiken.example1.reactionpoint.dto.ReactionPointDto;
import com.wiken.example1.reactionpoint.exception.PointNotFoundException;
import com.wiken.example1.reactionpoint.exception.ReactionPointEntityNotFoundException;
import com.wiken.example1.reactionpoint.service.ReactionPointService;
import com.wiken.example1.user.entity.SUser;
import com.wiken.example1.user.entity.UserEntity;
import com.wiken.example1.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

/**
 * 좋아요 싫어요 Controller
 */
@Controller
@RequestMapping("/reaction")
@RequiredArgsConstructor
public class ReactionPointController {

    private final ReactionPointService reactionPointService;
    private final UserService userService;

    @GetMapping
    public String clickReaction (
            @RequestParam("id") String relId,
            @RequestParam("type") RelType relType,
            @RequestParam("point") Point point,
            @AuthenticationPrincipal SUser user,
            @RequestParam("redirect") String redirectUri,
            RedirectAttributes redirectAttributes
    ) throws PointNotFoundException, ReactionPointEntityNotFoundException {

        /**
         * RelType이 존재하는지 여부
         */
        boolean existRelType = Arrays.stream(RelType.values())
                .anyMatch(r -> r.equals(relType));
        if (!existRelType) {
            redirectAttributes.addFlashAttribute("message", "잘못된 정보입니다");
            redirectAttributes.addAttribute("id", relId);
            return "redirect:" + redirectUri;
        }

        /**
         * 시큐리티에서 넘어온 정보를 가지고 user 정보 조회
         */
        UserEntity userEntity = userService.findUserByUserId(user.getUserId());

        /**
         * 파라미터 정보를 Dto에 담기
         */
        ReactionPointDto originalReactionPointDto = ReactionPointDto.builder()
                .relId(relId)
                .relType(relType)
                .point(point)
                .user(userEntity)
                .build();

        /**
         * 유저가 좋아요 또는 싫어요를 누른적이 있는지 확인
         */
        ReactionPointDto findReactionPointDto =
                reactionPointService.findReactionPoint(originalReactionPointDto);

        /**
         * 좋아요 또는 싫어요를 누른적이 없다면 ReactionPoint 바로 생성
         */
        if (findReactionPointDto == null) {
            reactionPointService.createReactionPoint(originalReactionPointDto);

            redirectAttributes.addAttribute("id", relId);
              return "redirect:" + redirectUri;
        }

        /**
         * ==
         *  - 좋아요를 눌렀는데 또 좋아요를 누르면 좋아요 취소
         *  - 싫어요를 눌렀는데 또 싫어요를 누르면 싫어요 취소
         * !=
         *  - 좋아요를 눌렀는데 싫어요를 눌렀다면 좋아요를 싫어요로 변경
         *  - 싫어요를 눌렀는데 좋아요를 눌렀다면 싫어요를 좋아요로 변경
         */
        if (originalReactionPointDto.getPoint() == findReactionPointDto.getPoint()) {
            reactionPointService.cancelReactionPointById(findReactionPointDto.getId());
        }
        else {
            reactionPointService.changeReactionPoint(findReactionPointDto);
        }

        redirectAttributes.addAttribute("id", relId);
        return "redirect:" + redirectUri;
    }
}
