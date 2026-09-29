package com.max.almacen.utils;

import com.max.almacen.exceptions.InvalidDataException;

import java.math.BigDecimal;

public class NumberCustomUtils {

    public static <N extends Number> void nonNullNum(N number, String msg){
        if (number == null)
            throw new InvalidDataException(msg);
    }

    public static void PositiveInt(Integer number, String msg){
        nonNullNum(number, msg);

        if (number <0)
            throw new InvalidDataException(msg);
    }

    public static void PositiveBigDecimal(BigDecimal number, String msg){
        nonNullNum(number, msg);

        if (number.compareTo(BigDecimal.ZERO)<0)
            throw new InvalidDataException(msg);
    }
}
