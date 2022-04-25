package com.wiken.example1.article.resolver;

import com.wiken.example1.article.entity.ArticleEntity;
import com.wiken.example1.article.repository.ArticleRepository;
import com.wiken.example1.user.entity.SUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

/**
 * article 권한이 있는 유저 resolver custom
 */
@RequiredArgsConstructor
public final class AuthenticationPrincipalArgumentResolverCustom implements HandlerMethodArgumentResolver {

    private final ArticleRepository articleRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return findMethodAnnotation(ArticleAuthenticationUser.class, parameter) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        /**
         * Authentication 추출
         *  - 비어있으면 null 반환
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        /**
         * Principal 추출
         */
        Object principal = authentication.getPrincipal();
        ArticleAuthenticationUser annotation = findMethodAnnotation(ArticleAuthenticationUser.class, parameter);

        /**
         *  - principal이 비어있는지 확인
         *  - 파라미터 타입 == principal 타입 확인
         */
        if (principal != null && !ClassUtils.isAssignable(parameter.getParameterType(), principal.getClass())) {
            return null;
        }

        /**
         * 파라미터에서 id 값 추출
         */
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String id = request.getParameter("id");

        /**
         * 없으면 null 반환
         */
        if (id == null) {
            return null;
        }

        /**
         * articleId로 article entity 찾기
         */
        ArticleEntity articleEntity = articleRepository.findByArticleId(id);
        SUser user = (SUser) principal;

        /**
         * 게시글이 없으면 null 반환
         */
        if (articleEntity == null) {
            return null;
        }

        /**
         * 게시글 권한이 없으면 null 반환
         */
        if(!user.getUserId().equals(articleEntity.getUser().getUserId())) {
            return null;
        }

        /**
         * 최종 principal 반환
         */
        return principal;
    }

    /**
     * 어노테이션 찾기
     */
    private <T extends Annotation> T findMethodAnnotation(Class<T> annotationClass, MethodParameter parameter) {
        T annotation = parameter.getParameterAnnotation(annotationClass);
        if (annotation != null) {
            return annotation;
        }
        Annotation[] annotationsToSearch = parameter.getParameterAnnotations();
        for (Annotation toSearch : annotationsToSearch) {
            annotation = AnnotationUtils.findAnnotation(toSearch.annotationType(), annotationClass);
            if (annotation != null) {
                return annotation;
            }
        }
        return null;
    }

}
