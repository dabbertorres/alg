package com.aleciverson.alg;

interface Expression
{
    double value(EnvironmentReadable env);

    class Value implements Expression
    {
        private double val;

        public Value(double val)
        {
            this.val = val;
        }

        @Override
        public double value(EnvironmentReadable env)
        {
            return val;
        }
    }

    class Variable implements Expression
    {
        private String name;

        public Variable(String name)
        {
            this.name = name;
        }

        @Override
        public double value(EnvironmentReadable env)
        {
            return env.get(name);
        }
    }

    class Addition implements Expression
    {
        private Expression lhs;
        private Expression rhs;

        public Addition(Expression lhs, Expression rhs)
        {
            this.lhs = lhs;
            this.rhs = rhs;
        }

        @Override
        public double value(EnvironmentReadable env)
        {
            return lhs.value(env) + rhs.value(env);
        }
    }

    class Subtraction implements Expression
    {
        private Expression lhs;
        private Expression rhs;

        public Subtraction(Expression lhs, Expression rhs)
        {
            this.lhs = lhs;
            this.rhs = rhs;
        }

        @Override
        public double value(EnvironmentReadable env)
        {
            return lhs.value(env) - rhs.value(env);
        }
    }

    class Multiplication implements Expression
    {
        private Expression lhs;
        private Expression rhs;

        public Multiplication(Expression lhs, Expression rhs)
        {
            this.lhs = lhs;
            this.rhs = rhs;
        }

        @Override
        public double value(EnvironmentReadable env)
        {
            return lhs.value(env) * rhs.value(env);
        }
    }

    class Division implements Expression
    {
        private Expression lhs;
        private Expression rhs;

        public Division(Expression lhs, Expression rhs)
        {
            this.lhs = lhs;
            this.rhs = rhs;
        }

        @Override
        public double value(EnvironmentReadable env)
        {
            return lhs.value(env) / rhs.value(env);
        }
    }

    class Power implements Expression
    {
        private Expression lhs;
        private Expression rhs;

        public Power(Expression lhs, Expression rhs)
        {
            this.lhs = lhs;
            this.rhs = rhs;
        }

        @Override
        public double value(EnvironmentReadable env)
        {
            return Math.pow(lhs.value(env), rhs.value(env));
        }
    }

    class Negative implements Expression
    {
        private Expression rhs;

        public Negative(Expression rhs)
        {
            this.rhs = rhs;
        }

        @Override
        public double value(EnvironmentReadable env)
        {
            return -1 * rhs.value(env);
        }
    }

    class Sine implements Expression
    {
        private Expression subExpr;

        public Sine(Expression subExpr)
        {
            this.subExpr = subExpr;
        }

        @Override
        public double value(EnvironmentReadable env)
        {
            double val = subExpr.value(env);

            // short circuit to work around floating point inaccuracy

            if (Math.abs(val) == Math.PI / 2.0)
                return 1.0;

            return Math.sin(val);
        }
    }

    class Cosine implements Expression
    {
        private Expression subExpr;

        public Cosine(Expression subExpr)
        {
            this.subExpr = subExpr;
        }

        @Override
        public double value(EnvironmentReadable env)
        {
            double val = subExpr.value(env);

            // short circuit to work around floating point inaccuracy

            if (Math.abs(val) == Math.PI / 2.0)
                return 0.0;

            return Math.cos(val);
        }
    }

    class Tangent implements Expression
    {
        private Expression subExpr;

        public Tangent(Expression subExpr)
        {
            this.subExpr = subExpr;
        }

        @Override
        public double value(EnvironmentReadable env)
        {
            double val = subExpr.value(env);

            // short circuit to work around floating point inaccuracy

            if (val == Math.PI / 2.0)
                return Double.POSITIVE_INFINITY;
            if (val == -Math.PI / 2.0)
                return Double.NEGATIVE_INFINITY;

            return Math.tan(val);
        }
    }

    class Log implements Expression
    {
        private Expression subExpr;

        public Log(Expression subExpr)
        {
            this.subExpr = subExpr;
        }

        @Override
        public double value(EnvironmentReadable env)
        {
            return Math.log10(subExpr.value(env));
        }
    }

    class NaturalLog implements Expression
    {
        private Expression subExpr;

        public NaturalLog(Expression subExpr)
        {
            this.subExpr = subExpr;
        }

        @Override
        public double value(EnvironmentReadable env)
        {
            return Math.log(subExpr.value(env));
        }
    }

    class SquareRoot implements Expression
    {
        private Expression subExpr;

        public SquareRoot(Expression subExpr)
        {
            this.subExpr = subExpr;
        }

        @Override
        public double value(EnvironmentReadable env)
        {
            return Math.sqrt(subExpr.value(env));
        }
    }
}
