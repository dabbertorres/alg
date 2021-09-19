package com.aleciverson.alg;

public class UnknownVariableException extends RuntimeException {
    public UnknownVariableException(String varName) {
        super(String.format("unknown variable '%s'", varName));
    }
}
