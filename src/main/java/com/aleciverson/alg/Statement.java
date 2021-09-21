package com.aleciverson.alg;

public class Statement {
  private String outVar;
  private Expression expression;

  public Statement(String outVar, Expression expression) {
    this.outVar = outVar;
    this.expression = expression;
  }

  public double resolve(EnvironmentReadable env) {
    return expression.value(env);
  }

  public String outputVariable() {
    return outVar;
  }
}
