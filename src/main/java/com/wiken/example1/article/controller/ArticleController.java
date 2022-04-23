package com.wiken.example1.article.controller;

import com.wiken.example1.article.dto.ArticleDto;
import com.wiken.example1.article.entity.ArticleEntity;
import com.wiken.example1.article.exception.ArticleNotFoundException;
import com.wiken.example1.article.service.ArticleService;
import com.wiken.example1.article.vo.RequestCreateArticle;
import com.wiken.example1.article.vo.RequestModifyArticle;
import com.wiken.example1.article.vo.ResponseDetailArticle;
import com.wiken.example1.article.vo.ResponseModifyArticle;
import com.wiken.example1.user.entity.SUser;
import com.wiken.example1.user.entity.UserEntity;
import com.wiken.example1.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;
    private final UserService userService;
    private final ModelMapper mapper;

    @GetMapping
    public String showArticle(Model model) {
        Iterable<ArticleEntity> articleList = articleService.findAllArticles();

        List<ResponseDetailArticle> result = new ArrayList<>();

        articleList.forEach(a ->
                result.add(mapper.map(a, ResponseDetailArticle.class))
        );

        model.addAttribute("articles", result);

        return "article/articleList";
    }

    @GetMapping("/create")
    public String createArticleForm(RequestCreateArticle requestCreateArticle) {
        return "article/createArticleForm";
    }

    @PostMapping("/create")
    public String createArticle(
            @ModelAttribute RequestCreateArticle requestCreateArticle,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal SUser user) {
        ArticleDto articleDto = mapper.map(requestCreateArticle, ArticleDto.class);
        UserEntity userEntity = userService.findUserByUserId(user.getUserId());
        articleDto.setUser(userEntity);

        ArticleDto savedArticleDto = articleService.createArticle(articleDto);

        redirectAttributes.addFlashAttribute("message", "게시글이 생성되었습니다.");
        redirectAttributes.addAttribute("id", savedArticleDto.getArticleId());
        return "redirect:/article/detail";
    }

    @GetMapping("/detail")
    public String articleDetail(
            @RequestParam("id") String articleId, Model model) throws ArticleNotFoundException {
        ArticleEntity articleEntity = articleService.findArticle(articleId);

        ResponseDetailArticle responseArticle = mapper.map(articleEntity, ResponseDetailArticle.class);

        model.addAttribute("article", responseArticle);

        return "article/articleDetail";
    }

    @GetMapping("/delete")
    public String deleteArticle(
            @RequestParam("id") String articleId,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal SUser user) throws ArticleNotFoundException {
        ArticleEntity articleEntity = articleService.findArticle(articleId);
        if(!user.getUserId().equals(articleEntity.getUser().getUserId())) {
            redirectAttributes.addFlashAttribute("message", "삭제할 권한이 없습니다.");
            return "redirect:/article";
        }

        articleService.deleteArticle(articleId);

        redirectAttributes.addFlashAttribute("message", "게시글이 삭제되었습니다.");
        return "redirect:/article";
    }

    @GetMapping("/modify")
    public String modifyArticle(
            @RequestParam("id") String articleId,
            RedirectAttributes redirectAttributes,
            Model model,
            @AuthenticationPrincipal SUser user) throws ArticleNotFoundException {

        ArticleEntity articleEntity = articleService.findArticle(articleId);

        if (!user.getUserId().equals(articleEntity.getUser().getUserId())) {
            redirectAttributes.addAttribute("id", articleEntity.getArticleId());
            redirectAttributes.addFlashAttribute("message", "수정 권한이 없습니다.");
            return "redirect:/article/detail";
        }

        ResponseModifyArticle responseArticle = mapper.map(articleEntity, ResponseModifyArticle.class);

        model.addAttribute("modifyArticle", responseArticle);

        return "article/modifyArticleForm";
    }

    @PostMapping("/modify")
    public String modifyArticle(
            @RequestParam("id") String articleId,
            @ModelAttribute("modifyArticle") RequestModifyArticle requestArticle,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal SUser user) throws ArticleNotFoundException {


        UserEntity userEntity = userService.findUserByUserId(user.getUserId());
        if (!userEntity.getUserId().equals(user.getUserId())) {
            redirectAttributes.addAttribute("id", requestArticle.getArticleId());
            redirectAttributes.addFlashAttribute("message", "수정 권한이 없습니다.");
            return "redirect:/article/detail";
        }

        ArticleDto articleDto = mapper.map(requestArticle, ArticleDto.class);
        articleDto.setArticleId(articleId);

        articleService.modifyArticle(articleDto);

        redirectAttributes.addAttribute("id", articleId);
        return "redirect:/article/detail";
    }
}
