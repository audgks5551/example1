package com.wiken.example1.article.controller;

import com.wiken.example1.article.dto.ArticleDto;
import com.wiken.example1.article.entity.ArticleEntity;
import com.wiken.example1.article.service.ArticleService;
import com.wiken.example1.article.vo.RequestArticle;
import com.wiken.example1.article.vo.ResponseArticle;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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
            RedirectAttributes redirectAttributes) {
        ArticleDto articleDto = mapper.map(requestArticle, ArticleDto.class);
        articleDto.setUserId("EXAMPLE-USER-UUID1");

        articleService.createArticle(articleDto);

        redirectAttributes.addFlashAttribute("message", "게시글이 생성되었습니다.");

        return "redirect:/article";
    }

}
