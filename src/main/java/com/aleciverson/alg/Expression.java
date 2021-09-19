package com.aleciverson.alg;

import java.util.function.Function;

interface Expression {
    double value(EnvironmentReadable env);

    class Value implements Expression {
        private double val;

        public Value(double val) {
            this.val = val;
        }

        @Override
        public double value(EnvironmentReadable env) {
            return val;
        }
    }

    class Variable implements Expression {
        private String name;

        public Variable(String name) {
            this.name = name;
        }

        @Override
        public double value(EnvironmentReadable env) {
            return env.get(name);
        }
    }

    class Addition implements Expression {
        private Expression lhs;
        private Expression rhs;

        public Addition(Expression lhs, Expression rhs) {
            this.lhs = lhs;
            this.rhs = rhs;
        }

        @Override
        public double value(EnvironmentReadable env) {
            return lhs.value(env) + rhs.value(env);
        }
    }

    class Subtraction implements Expression {
        private Expression lhs;
        private Expression rhs;

        public Subtraction(Expression lhs, Expression rhs) {
            this.lhs = lhs;
            this.rhs = rhs;
        }

        @Override
        public double value(EnvironmentReadable env) {
            return lhs.value(env) - rhs.value(env);
        }
    }

    class Multiplication implements Expression {
        private Expression lhs;
        private Expression rhs;

        public Multiplication(Expression lhs, Expression rhs) {
            this.lhs = lhs;
            this.rhs = rhs;
        }

        @Override
        public double value(EnvironmentReadable env) {
            return lhs.value(env) * rhs.value(env);
        }
    }

    class Division implements Expression {
        private Expression lhs;
        private Expression rhs;

        public Division(Expression lhs, Expression rhs) {
            this.lhs = lhs;
            this.rhs = rhs;
        }

        @Override
        public double value(EnvironmentReadable env) {
            return lhs.value(env) / rhs.value(env);
        }
    }

    class Power implements Expression {
        private Expression lhs;
        private Expression rhs;

        public Power(Expression lhs, Expression rhs) {
            this.lhs = lhs;
            this.rhs = rhs;
        }

        @Override
        public double value(EnvironmentReadable env) {
            return Math.pow(lhs.value(env), rhs.value(env));
        }
    }

    class Negative implements Expression {
        private Expression rhs;

        public Negative(Expression rhs) {
            this.rhs = rhs;
        }

        @Override
        public double value(EnvironmentReadable env) {
            return -1 * rhs.value(env);
        }
    }

    class BuiltinCall implements Expression {
        private Function<Double, Double> func;
        private Expression arg;

        public BuiltinCall(Function<Double, Double> func, Expression arg) {
            this.func = func;
            this.arg = arg;
        }

        @Override
        public double value(EnvironmentReadable env) {
            double argVal = arg.value(env);
            return func.apply(argVal);
        }
    }

    class Sine extends BuiltinCall {
        public Sine(Expression subExpr) {
            super(Math::sin, subExpr);
        }
    }

    class Cosine extends BuiltinCall {
        public Cosine(Expression subExpr) {
            super(Math::cos, subExpr);
        }
    }

    class Tangent extends BuiltinCall {
        public Tangent(Expression subExpr) {
            super(Math::tan, subExpr);
        }
    }

    class Log extends BuiltinCall {
        public Log(Expression subExpr) {
            super(Math::log10, subExpr);
        }
    }

    class NaturalLog extends BuiltinCall {
        public NaturalLog(Expression subExpr) {
            super(Math::log, subExpr);
        }
    }

    class SquareRoot extends BuiltinCall {
        public SquareRoot(Expression subExpr) {
            super(Math::sqrt, subExpr);
        }
    }
}
