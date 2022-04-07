package se.su.joos1190;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Stack<Double> stk = new Stack<>(Double.class);

        while (scanner.hasNext()) {
            String item = scanner.next();
            try {
                double val = Double.parseDouble(item);
                stk.push(val);
            } catch (NumberFormatException e) {
                switch (item) {
                    case "+" -> {
                        double a = stk.pop();
                        double b = stk.pop();
                        stk.push(b + a);
                    }
                    case "-" -> {
                        double a = stk.pop();
                        double b = stk.pop();
                        stk.push(b - a);
                    }
                    case "*" -> {
                        double a = stk.pop();
                        double b = stk.pop();
                        stk.push(b * a);
                    }
                    case "/" -> {
                        double a = stk.pop();
                        double b = stk.pop();
                        stk.push(b / a);
                    }
                    case "=" -> {
                        double a = stk.pop();
                        System.out.println(a);
                    }
                }
            }
        }
    }
}
