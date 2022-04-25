package com.wiken.example1.base.config;

import com.wiken.example1.article.repository.ArticleRepository;
import com.wiken.example1.article.resolver.AuthenticationPrincipalArgumentResolverCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ArticleRepository articleRepository;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationPrincipalArgumentResolverCustom());
    }

    @Bean
    public AuthenticationPrincipalArgumentResolverCustom authenticationPrincipalArgumentResolverCustom() {
        return new AuthenticationPrincipalArgumentResolverCustom(articleRepository);
    }
}
