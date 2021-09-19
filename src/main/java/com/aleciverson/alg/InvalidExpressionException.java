package com.aleciverson.alg;

public class InvalidExpressionException extends RuntimeException
{
    public InvalidExpressionException(String expr)
    {
        super(String.format("invalid expression '%s'", expr));
    }
}
