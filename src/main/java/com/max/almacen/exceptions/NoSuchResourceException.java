package com.max.almacen.exceptions;

public class NoSuchResourceException extends RuntimeException {
    public NoSuchResourceException(String message) {
        super(message);
    }
}
