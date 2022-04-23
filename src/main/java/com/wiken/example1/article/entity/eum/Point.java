package com.wiken.example1.article.entity.eum;

import com.wiken.example1.article.exception.PointServiceException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Point {
    GOOD(1, "좋아요"),
    BAD(-1, "싫어요");

    private Integer code;
    private String value;

    Point(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public static Point ofCode(Integer code) throws PointServiceException {
        return Arrays.stream(Point.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new PointServiceException(
                        String.format("상태코드에 code=[%s]가 존재하지 않습니다", code)
                ));
    }
}
