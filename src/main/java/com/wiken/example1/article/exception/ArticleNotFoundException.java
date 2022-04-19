package com.wiken.example1.article.exception;

public class ArticleNotFoundException extends Throwable {
    public ArticleNotFoundException(String message) {
        super(message);
    }
}
