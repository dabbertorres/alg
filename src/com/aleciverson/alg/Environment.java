package com.aleciverson.alg;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;

interface EnvironmentReadable
{
    double get(String name);
}

interface EnvironmentWritable extends EnvironmentReadable
{
    void set(String name, double value);
}

public class Environment implements EnvironmentReadable, EnvironmentWritable, Iterable<Map.Entry<String, Double>>
{
    private final Map<String, Double> variables = new HashMap<>();

    public Environment(Map<String, Double> initial)
    {
        // add constants
        variables.put("pi", Math.PI);
        variables.put("PI", Math.PI);
        variables.put("e", Math.E);
        variables.put("E", Math.E);

        if (initial != null)
            variables.putAll(initial);
    }

    public double get(String name)
    {
        if (variables.containsKey(name))
            return variables.get(name);
        else
            throw new UnknownVariableException(name);
    }

    public void set(String name, double value)
    {
        variables.put(name, value);
    }

    @Override
    public Iterator<Map.Entry<String, Double>> iterator()
    {
        return variables.entrySet().iterator();
    }

    @Override
    public void forEach(Consumer<? super Map.Entry<String, Double>> action)
    {
        variables.entrySet().forEach(action);
    }

    @Override
    public Spliterator<Map.Entry<String, Double>> spliterator()
    {
        return variables.entrySet().spliterator();
    }
}
