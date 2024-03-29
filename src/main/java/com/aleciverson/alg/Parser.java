package com.aleciverson.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Parser {
  public static Statement parse(String line) {
    int equalsIdx = line.indexOf('=');

    if (equalsIdx != -1) {
      String outVar = line.substring(0, equalsIdx).trim();

      if (isConstant(outVar))
        throw new AssignConstantException(outVar);

      Expression expr = parseExpression(line.substring(equalsIdx + 1).trim());

      return new Statement(outVar, expr);
    } else {
      // all we're doing is getting the value of a variable
      String var = line.trim();
      return new Statement(var, new Expression.Variable(var));
    }
  }

  public static List<Statement> parse(List<String> lines) {
    List<Statement> statements = new ArrayList<>();

    for (String line : lines)
      statements.add(parse(line));

    return statements;
  }

  private static Expression parseExpression(String expr) {
    Stack<Expression> output = new Stack<>();
    Stack<Token> operators = new Stack<>();

    // starts true due to assignment sort of being an operator
    boolean previousOperator = true;

    int i = 0;
    while (i < expr.length()) {
      char token = expr.charAt(i);

      int end;
      Expression value;

      switch (token) {
      case '0':
      case '1':
      case '2':
      case '3':
      case '4':
      case '5':
      case '6':
      case '7':
      case '8':
      case '9':
        end = parseNumber(expr, i);
        value = new Expression.Value(Double.parseDouble(expr.substring(i, end)));
        // try and pop negative operators if possible
        while (!operators.empty() && operators.peek() == Token.NEGATIVE) {
          value = new Expression.Negative(value);
          operators.pop();
        }
        output.add(value);
        i = end;
        previousOperator = false;
        break;

      case '+':
        parseOperator(output, operators);
        operators.push(Token.PLUS);
        previousOperator = true;
        ++i;
        break;

      case '-':
        if (previousOperator) {
          // it's a unary minus (negative)
          operators.push(Token.NEGATIVE);
        } else {
          parseOperator(output, operators);
          operators.push(Token.MINUS);
        }
        previousOperator = true;
        ++i;
        break;

      case '*':
        parseOperator(output, operators, Token.PLUS, Token.MINUS);
        operators.push(Token.MULTIPLY);
        previousOperator = true;
        ++i;
        break;

      case '/':
        parseOperator(output, operators, Token.PLUS, Token.MINUS);
        operators.push(Token.DIVIDE);
        previousOperator = true;
        ++i;
        break;

      case '^':
        parseOperator(output, operators, Token.PLUS, Token.MINUS, Token.MULTIPLY, Token.DIVIDE);
        operators.push(Token.POWER);
        previousOperator = true;
        ++i;
        break;

      case '(':
        operators.push(Token.PAREN_OPEN);
        previousOperator = true;
        ++i;
        break;

      case ')':
        parseOperator(output, operators);
        operators.pop();
        while (!operators.empty() && operators.peek().isFunction()) {
          Expression func = addFunction(operators.pop(), output.pop());
          output.push(func);
        }
        ++i;

        previousOperator = false;
        break;

      case ' ':
        // we can just skip whitespace
        ++i;
        break;

      default:
        end = parseIdentifier(expr, i);
        if (end != i) {
          String identifier = expr.substring(i, end);
          Token function = switch (identifier) {
          case "sin" -> Token.SINE;
          case "cos" -> Token.COSINE;
          case "tan" -> Token.TANGENT;
          case "log" -> Token.LOG;
          case "ln" -> Token.NATURAL_LOG;
          case "sqrt" -> Token.SQUARE_ROOT;
          default -> null;
          };

          if (function != null) {
            operators.push(function);
            previousOperator = true;
          } else {
            output.add(new Expression.Variable(identifier));
            previousOperator = false;
          }

          i = end;
        } else {
          throw new UnexpectedTokenException(token + "");
        }
        break;
      }
    }

    try {
      while (!operators.empty()) {
        Token op = operators.pop();
        if (op.isFunction()) {
          Expression subExpr = output.pop();
          output.push(addFunction(op, subExpr));
        } else {
          Expression rhs = output.pop();
          Expression lhs = output.pop();
          output.push(addOperator(op, lhs, rhs));
        }
      }
    } catch (Exception e) {
      throw new InvalidExpressionException(expr);
    }

    if (output.size() != 1)
      throw new InvalidExpressionException(expr);

    return output.pop();
  }

  private static void parseOperator(Stack<Expression> output, Stack<Token> operators, Token... endConditions) {
    while (!operators.empty()) {
      Token top = operators.peek();
      if (top == Token.PAREN_OPEN || Arrays.stream(endConditions).anyMatch(op -> top == op)) {
        break;
      } else {
        Expression rhs = output.pop();
        Expression lhs = output.pop();
        Expression op = addOperator(operators.pop(), lhs, rhs);
        output.push(op);
      }
    }
  }

  private static Expression addOperator(Token op, Expression lhs, Expression rhs) {
    switch (op) {
    case PLUS:
      return new Expression.Addition(lhs, rhs);
    case MINUS:
      return new Expression.Subtraction(lhs, rhs);
    case MULTIPLY:
      return new Expression.Multiplication(lhs, rhs);
    case DIVIDE:
      return new Expression.Division(lhs, rhs);
    case POWER:
      return new Expression.Power(lhs, rhs);
    default:
      throw new UnexpectedTokenException(op.toString());
    }
  }

  private static Expression addFunction(Token func, Expression subExpr) {
    switch (func) {
    case SINE:
      return new Expression.Sine(subExpr);
    case COSINE:
      return new Expression.Cosine(subExpr);
    case TANGENT:
      return new Expression.Tangent(subExpr);
    case LOG:
      return new Expression.Log(subExpr);
    case NATURAL_LOG:
      return new Expression.NaturalLog(subExpr);
    case SQUARE_ROOT:
      return new Expression.SquareRoot(subExpr);
    default:
      throw new UnexpectedTokenException(func.toString());
    }
  }

  private static int parseIdentifier(String line, int index) {
    int i = index;

    while (i < line.length()) {
      char ch = line.charAt(i);

      if ('a' <= ch && ch <= 'z' || 'A' <= ch && ch <= 'Z' || ch == '_')
        ++i;
      else
        break;
    }

    return i;
  }

  private static int parseNumber(String line, int index) {
    boolean hasPeriod = false;

    int i = index;

    while (i < line.length()) {
      switch (line.charAt(i)) {
      case '0':
      case '1':
      case '2':
      case '3':
      case '4':
      case '5':
      case '6':
      case '7':
      case '8':
      case '9':
        ++i;
        break;

      case '.':
        if (!hasPeriod) {
          hasPeriod = true;
          ++i;
        } else {
          return i;
        }
        break;

      default:
        return i;
      }
    }

    return i;
  }

  private static boolean isConstant(String variable) {
    switch (variable) {
    case "pi":
    case "PI":
    case "e":
    case "E":
      return true;

    default:
      return false;
    }
  }
}
