package com.wiken.example1.article.controller;

import com.wiken.example1.article.dto.ArticleDto;
import com.wiken.example1.article.entity.ArticleEntity;
import com.wiken.example1.article.exception.ArticleNotFoundException;
import com.wiken.example1.article.resolver.ArticleAuthenticationUser;
import com.wiken.example1.article.service.ArticleService;
import com.wiken.example1.article.vo.*;
import com.wiken.example1.reply.vo.RequestReply;
import com.wiken.example1.user.entity.SUser;
import com.wiken.example1.user.entity.UserEntity;
import com.wiken.example1.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
     * article list
     */
    @GetMapping
    public String showArticle(
            @RequestParam(value="page", defaultValue="0") int page,
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            Model model) {
        Page<ArticleDto> articleList = articleService.findAllArticlesWithPage(page, search);
        long totalElements = articleList.getTotalElements();
        List<ResponseListArticle> result = new ArrayList<>();
        articleList.forEach(a ->
                result.add(mapper.map(a, ResponseListArticle.class))
        );

        model.addAttribute("articles", result);
        model.addAttribute("page", new PageableInfo(articleList));
        model.addAttribute("search", search);
        return "article/articleList";
    }

    /**
     * article create
     */
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

    /**
     * article detail
     */
    @GetMapping("/detail")
    public String articleDetail(
            @RequestParam("id") String articleId, Model model) throws ArticleNotFoundException {
        ArticleDto articleDto = articleService.findArticle(articleId);

        ResponseDetailArticle responseArticle = mapper.map(articleDto, ResponseDetailArticle.class);

        model.addAttribute("article", responseArticle);
        model.addAttribute("requestReply", new RequestReply());

        return "article/articleDetail";
    }

    /**
     * article delete
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete")
    public String deleteArticle(
            @RequestParam("id") String articleId,
            RedirectAttributes redirectAttributes,
            @ArticleAuthenticationUser SUser user) throws ArticleNotFoundException {

        if(user == null) {
            redirectAttributes.addFlashAttribute("message", "삭제할 권한이 없습니다.");
            return "redirect:/article";
        }

        articleService.deleteArticle(articleId);

        redirectAttributes.addFlashAttribute("message", "게시글이 삭제되었습니다.");
        return "redirect:/article";
    }

    /**
     * article modify form
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify")
    public String modifyArticle(
            @RequestParam("id") String articleId,
            RedirectAttributes redirectAttributes,
            Model model,
            @ArticleAuthenticationUser SUser user) throws ArticleNotFoundException {

        ArticleDto articleDto = articleService.findArticle(articleId);

        if (user == null) {
            redirectAttributes.addAttribute("id", articleDto.getArticleId());
            redirectAttributes.addFlashAttribute("message", "수정 권한이 없습니다.");
            return "redirect:/article/detail";
        }

        ResponseModifyArticle responseArticle = mapper.map(articleDto, ResponseModifyArticle.class);

        model.addAttribute("modifyArticle", responseArticle);

        return "article/modifyArticleForm";
    }

    /**
     * article modify
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    public String modifyArticle(
            @RequestParam("id") String articleId,
            @ModelAttribute("modifyArticle") RequestModifyArticle requestArticle,
            RedirectAttributes redirectAttributes,
            @ArticleAuthenticationUser SUser user) throws ArticleNotFoundException {

        if (user == null) {
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
