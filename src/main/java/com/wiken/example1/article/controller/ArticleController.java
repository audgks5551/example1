package com.wiken.example1.article.controller;

import com.wiken.example1.article.entity.ArticleEntity;
import com.wiken.example1.article.service.ArticleService;
import com.wiken.example1.article.vo.ResponseArticle;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
                mapper.map(a, ResponseArticle.class)
        );

        model.addAttribute("articles", result);

        return "/article/articleList";
    }

    @GetMapping("/create")
    public String createArticleForm() {
        return "/article/createArticleForm";
    }

}
