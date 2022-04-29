package com.wiken.example1.reply.controller;

import com.wiken.example1.reactionpoint.entity.eum.RelType;
import com.wiken.example1.reply.entity.ReplyEntity;
import com.wiken.example1.reply.service.ReplyService;
import com.wiken.example1.reply.vo.RequestReply;
import com.wiken.example1.reply.vo.ResponseReply;
import com.wiken.example1.user.entity.SUser;
import com.wiken.example1.user.entity.UserEntity;
import com.wiken.example1.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

@Controller
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final UserService userService;
    private final ReplyService replyService;
    private final ModelMapper mapper;

    /**
     * 답변 생성
     */
    @PostMapping
    public String createReply(
            @ModelAttribute RequestReply requestReply,
            @AuthenticationPrincipal SUser user,
            RedirectAttributes redirectAttributes) {

        if (user == null) {
            redirectAttributes.addFlashAttribute("message", "로그인이 필요합니다.");
            redirectAttributes.addAttribute("id", requestReply.getRelId());
            return "redirect:" + requestReply.getRedirectUri();
        }

        /**
         * RelType이 존재하는지 여부
         */
        boolean existRelType = Arrays.stream(RelType.values())
                .anyMatch(r -> r.equals(requestReply.getRelType()));
        if (!existRelType) {
            redirectAttributes.addFlashAttribute("message", "잘못된 정보입니다");
            redirectAttributes.addAttribute("id", requestReply.getRelId());
            return "redirect:" + requestReply.getRedirectUri();
        }

        UserEntity userEntity = userService.findUserByUserId(user.getUserId());

        ReplyEntity replyEntity = mapper.map(requestReply, ReplyEntity.class);
        replyEntity.setUser(userEntity);

        replyService.createReply(replyEntity);

        redirectAttributes.addFlashAttribute("message", "게시글이 생성되엇습니다.");
        redirectAttributes.addAttribute("id", requestReply.getRelId());
        return "redirect:" + requestReply.getRedirectUri();
    }

    /**
     * 답변 리스팅
     */
    @GetMapping
    public ResponseEntity<ResponseReply> listReplies(int age) {
        ResponseReply responseReply = new ResponseReply();
        responseReply.setAge(age);
        return ResponseEntity.ok(responseReply);
    }

    /**
     * 테스트 페이지
     */
    @GetMapping("/ajax")
    public String replyTest() {
        return "template/ajax";
    }
}
