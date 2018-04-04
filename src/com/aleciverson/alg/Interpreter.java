package com.aleciverson.alg;

import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Interpreter implements Iterable<Map.Entry<String, Double>>
{
    private Environment environment;

    public Interpreter(Environment env)
    {
        this.environment = env;
    }

    public double run(Statement stmt)
    {
        double value = stmt.resolve(environment);
        environment.set(stmt.outputVariable(), value);
        return value;
    }

    public double get(String var)
    {
        return environment.get(var);
    }

    @Override
    public Iterator<Map.Entry<String, Double>> iterator()
    {
        return environment.iterator();
    }

    @Override
    public void forEach(Consumer<? super Map.Entry<String, Double>> action)
    {
        environment.forEach(action);
    }

    @Override
    public Spliterator<Map.Entry<String, Double>> spliterator()
    {
        return environment.spliterator();
    }
}
