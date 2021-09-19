package com.aleciverson.alg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        try {
            if (args == null || args.length == 0) {
                repl();
            } else {
                Environment env = new Environment(null);

                if (args.length > 1) {
                    Arrays.stream(args, 1, args.length).forEach(s -> {
                        String[] comps = s.split("=");
                        env.set(comps[0], Double.parseDouble(comps[1]));
                    });
                }

                runFile(args[0], env);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.toString());
            System.exit(1);
        }
    }

    public static void repl() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        Interpreter interpreter = new Interpreter(new Environment(null));

        while (true) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null)
                return;

            switch (line) {
            case "exit":
                return;

            case "env":
                for (Map.Entry<String, Double> var : interpreter.getEnvironment())
                    printVar(var.getKey(), var.getValue());
                break;

            case "help":
                System.out.println("commands:");
                System.out.println("\tenv - print all existing variables and its value");
                System.out.println("\thelp - print this message");
                System.out.println("\texit - exit the interative repl");
                break;

            default:
                try {
                    Statement stmt = Parser.parse(line);
                    double result = interpreter.run(stmt);
                    printVar(stmt.outputVariable(), result);
                } catch (AssignConstantException | InvalidExpressionException | UnexpectedTokenException
                        | UnknownVariableException e) {
                    System.out.println(e.toString());
                }
                break;
            }
        }
    }

    public static void runFile(String file, Environment env) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(file));
        List<Statement> stmts = Parser.parse(lines);

        Interpreter interpreter = new Interpreter(env);

        for (Statement stmt : stmts) {
            double result = interpreter.run(stmt);
            printVar(stmt.outputVariable(), result);
        }
    }

    private static void printVar(String var, double value) {
        System.out.printf("%s = %f\n", var, value);
    }
}
