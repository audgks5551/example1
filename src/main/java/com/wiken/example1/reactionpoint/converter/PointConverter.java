package com.wiken.example1.reactionpoint.converter;

import com.wiken.example1.reactionpoint.entity.eum.Point;
import com.wiken.example1.reactionpoint.exception.PointServiceException;
import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;

public class PointConverter implements AttributeConverter<Point, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Point attribute) {
        return attribute.getCode();
    }

    @SneakyThrows(PointServiceException.class)
    @Override
    public Point convertToEntityAttribute(Integer dbData) {
        return Point.ofCode(dbData);
    }
}
