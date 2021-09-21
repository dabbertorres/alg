package com.aleciverson.alg;

public class AssignConstantException extends RuntimeException {
  public AssignConstantException(String constant) {
    super(String.format("cannot assign to constant '%s'", constant));
  }
}
