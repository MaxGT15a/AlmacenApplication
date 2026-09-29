package com.max.almacen.utils;

import com.max.almacen.exceptions.InvalidDataException;

public class StringCustomUtils {
    public static void nonEmpty(String text, String msg){
        if (text == null || text.trim().isBlank())
            throw new InvalidDataException(msg);
    }

    public static void validateSize(String text, Integer min, Integer max, String msg){
        nonEmpty(text, msg);

        if (text.length()<min || text.length()>max)
            throw new InvalidDataException(msg);
    }

    public static String normalizeText(String text){
        return text.toLowerCase()
                .replace("á","a").replace("é","e")
                .replace("í","i").replace("ó","o")
                .replace("ú","u").replace("ü","u")
                .replace("ñ","ni");
    }
}
