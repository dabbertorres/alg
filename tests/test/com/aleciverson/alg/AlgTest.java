package test.com.aleciverson.alg;

import java.util.ArrayList;
import java.util.List;

import com.aleciverson.alg.Environment;
import com.aleciverson.alg.Interpreter;
import com.aleciverson.alg.Parser;
import com.aleciverson.alg.Statement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlgTest
{
    @Test
    void add()
    {
        Interpreter interpreter = new Interpreter(new Environment(null));
        Statement   stmt        = Parser.parse("x = 5 + 2");
        double      result      = interpreter.run(stmt);

        assertEquals(7.0, result);
    }

    @Test
    void sub()
    {
        Interpreter interpreter = new Interpreter(new Environment(null));
        Statement   stmt        = Parser.parse("x = 5 - 2");
        double      result      = interpreter.run(stmt);

        assertEquals(3.0, result);
    }

    @Test
    void multiply()
    {
        Interpreter interpreter = new Interpreter(new Environment(null));
        Statement   stmt        = Parser.parse("x = 5 * 2");
        double      result      = interpreter.run(stmt);

        assertEquals(10.0, result);
    }

    @Test
    void divide()
    {
        Interpreter interpreter = new Interpreter(new Environment(null));
        Statement   stmt        = Parser.parse("x = 5 / 2");
        double      result      = interpreter.run(stmt);

        assertEquals(2.5, result);
    }

    @Test
    void power()
    {
        Interpreter interpreter = new Interpreter(new Environment(null));
        Statement   stmt        = Parser.parse("x = 5 ^ 2");
        double      result      = interpreter.run(stmt);

        assertEquals(25.0, result);
    }

    @Test
    void sine()
    {
        Interpreter interpreter = new Interpreter(new Environment(null));

        List<String> lines = new ArrayList<>();
        lines.add("x = sin(0)");
        lines.add("y = sin(pi / 2)");

        List<Statement> stmts = Parser.parse(lines);

        for (Statement stmt : stmts)
            interpreter.run(stmt);

        double x = interpreter.get("x");
        double y = interpreter.get("y");

        assertEquals(0.0, x, Math.ulp(x));
        assertEquals(1.0, y, Math.ulp(y));
    }

    @Test
    void cosine()
    {
        Interpreter interpreter = new Interpreter(new Environment(null));

        List<String> lines = new ArrayList<>();
        lines.add("x = cos(0)");
        lines.add("y = cos(pi / 2)");

        List<Statement> stmts = Parser.parse(lines);

        for (Statement stmt : stmts)
            interpreter.run(stmt);

        double x = interpreter.get("x");
        double y = interpreter.get("y");

        assertEquals(1.0, x, Math.ulp(x));
        assertEquals(0.0, y, Math.ulp(y));
    }

    @Test
    void tangent()
    {
        Interpreter interpreter = new Interpreter(new Environment(null));

        List<String> lines = new ArrayList<>();
        lines.add("x = tan(0)");
        lines.add("y = tan(pi / 2)");

        List<Statement> stmts = Parser.parse(lines);

        for (Statement stmt : stmts)
            interpreter.run(stmt);

        double x = interpreter.get("x");
        double y = interpreter.get("y");

        assertEquals(0.0, x, Math.ulp(x));
        assertEquals(0.0, y, Math.ulp(y));
    }

    @Test
    void log()
    {
        Interpreter interpreter = new Interpreter(new Environment(null));

        List<String> lines = new ArrayList<>();
        lines.add("x = log(10)");
        lines.add("y = log(100)");

        List<Statement> stmts = Parser.parse(lines);

        for (Statement stmt : stmts)
            interpreter.run(stmt);

        double x = interpreter.get("x");
        double y = interpreter.get("y");

        assertEquals(1.0, x, Math.ulp(x));
        assertEquals(2.0, y, Math.ulp(y));
    }

    @Test
    void naturalLog()
    {
        Interpreter interpreter = new Interpreter(new Environment(null));

        List<String> lines = new ArrayList<>();
        lines.add("x = ln(e)");
        lines.add("y = ln(e ^ 2)");

        List<Statement> stmts = Parser.parse(lines);

        for (Statement stmt : stmts)
            interpreter.run(stmt);

        double x = interpreter.get("x");
        double y = interpreter.get("y");

        assertEquals(1.0, x, Math.ulp(x));
        assertEquals(2.0, y, Math.ulp(y));
    }

    @Test
    void complex()
    {
        Interpreter interpreter = new Interpreter(new Environment(null));

        List<String> lines = new ArrayList<>();
        lines.add("x = 3");
        lines.add("y = 2");
        lines.add("z = sin((10 - x) * pi / y ^ 2)");

        List<Statement> stmts = Parser.parse(lines);

        for (Statement stmt : stmts)
            interpreter.run(stmt);

        double x = interpreter.get("x");
        double y = interpreter.get("y");
        double z = interpreter.get("z");

        assertEquals(3.0, x, Math.ulp(x));
        assertEquals(2.0, y, Math.ulp(y));
        assertEquals(-Math.sqrt(2.0) / 2.0, z, Math.ulp(z));
    }
}
