package com.aleciverson.alg;

public class Interpreter {
  private Environment environment;

  public Interpreter(Environment env) {
    this.environment = env;
  }

  public double run(Statement stmt) {
    double value = stmt.resolve(environment);
    environment.set(stmt.outputVariable(), value);
    return value;
  }

  public double get(String var) {
    return environment.get(var);
  }

  public EnvironmentReadable getEnvironment() {
    return environment;
  }
}
