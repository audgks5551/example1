package com.wiken.example1.article.controller;

import com.wiken.example1.article.dto.ArticleDto;
import com.wiken.example1.article.entity.ArticleEntity;
import com.wiken.example1.article.exception.ArticleNotFoundException;
import com.wiken.example1.article.service.ArticleService;
import com.wiken.example1.article.vo.RequestArticle;
import com.wiken.example1.article.vo.ResponseArticle;
import com.wiken.example1.user.entity.SUser;
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
    private final ModelMapper mapper;

    @GetMapping
    public String showArticle(Model model) {
        Iterable<ArticleEntity> articleList = articleService.findAllArticles();

        List<ResponseArticle> result = new ArrayList<>();

        articleList.forEach(a ->
                result.add(mapper.map(a, ResponseArticle.class))
        );

        model.addAttribute("articles", result);

        return "article/articleList";
    }

    @GetMapping("/create")
    public String createArticleForm(RequestArticle requestArticle) {
        return "article/createArticleForm";
    }

    @PostMapping("/create")
    public String createArticle(
            @ModelAttribute RequestArticle requestArticle,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal SUser user) {
        ArticleDto articleDto = mapper.map(requestArticle, ArticleDto.class);
        articleDto.setUserId(user.getUserId());

        articleService.createArticle(articleDto);

        redirectAttributes.addFlashAttribute("message", "게시글이 생성되었습니다.");

        return "redirect:/article";
    }

    @GetMapping("/{articleId}")
    public String articleDetail(
            @PathVariable(value = "articleId") String articleId,
            Model model) throws ArticleNotFoundException {
        ArticleEntity articleEntity = articleService.findArticle(articleId);

        ResponseArticle responseArticle = mapper.map(articleEntity, ResponseArticle.class);

        model.addAttribute("article", responseArticle);

        return "article/articleDetail";
    }

    @GetMapping("/delete/{articleId}")
    public String deleteArticle(
            @PathVariable(value = "articleId") String articleId,
            RedirectAttributes redirectAttributes) throws ArticleNotFoundException {

        articleService.deleteArticle(articleId);

        redirectAttributes.addFlashAttribute("message", "게시글이 삭제되었습니다.");

        return "redirect:/article";
    }

}
