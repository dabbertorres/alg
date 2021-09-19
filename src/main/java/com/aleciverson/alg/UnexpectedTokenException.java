package com.aleciverson.alg;

public class UnexpectedTokenException extends RuntimeException {
    public UnexpectedTokenException(String str) {
        super(String.format("unexpected token '%s'", str));
    }
}
