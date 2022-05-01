package com.wiken.example1.article.vo;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class PageableInfo {
    private boolean isExist;
    private Long totalArticles;
    private int totalPages;
    private int size;
    private int currentNumber;
    private boolean haveNoPrevious;
    private boolean haveNoNext;
    private int previous;
    private int next;
    private int startPoint;

    public PageableInfo(Page<?> list) {
        this.isExist = !list.isEmpty();
        this.totalArticles = list.getTotalElements();
        this.totalPages = list.getTotalPages();
        this.totalArticles = list.getTotalElements();
        this.size = list.getSize();
        this.currentNumber = list.getNumber() + 1;
        this.haveNoPrevious = !list.hasPrevious();
        this.haveNoNext = !list.hasNext();
        this.previous = list.hasPrevious() ? list.getNumber() - 1 : 0;
        this.next = list.hasNext() ? list.getNumber() + 1 : 0;
        this.startPoint = list.getSize() * (list.getNumber());
    }
}
