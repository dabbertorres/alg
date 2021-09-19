package com.aleciverson.alg;

enum Token {
    PLUS, MINUS, MULTIPLY, DIVIDE, POWER, NEGATIVE, SINE, COSINE, TANGENT, LOG, NATURAL_LOG, SQUARE_ROOT, PAREN_OPEN;

    public boolean isFunction() {
        return this == SINE || this == COSINE || this == TANGENT || this == LOG || this == NATURAL_LOG
                || this == SQUARE_ROOT;
    }
}
