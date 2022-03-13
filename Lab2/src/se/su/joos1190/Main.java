package se.su.joos1190;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useDelimiter("\\s+");
        Stack stk = new Stack();

        while (scanner.hasNext()) {
            String item = scanner.next();
            try {
                double val = Double.parseDouble(item);
                stk.push(val);
            } catch (NumberFormatException e) {
                switch (item) {
                    case "+": {
                        double a = stk.pop();
                        double b = stk.pop();
                        stk.push(a + b);
                    }
                    break;

                    case "-": {
                        double a = stk.pop();
                        double b = stk.pop();
                        stk.push(a - b);
                    }
                    break;

                    case "*": {
                        double a = stk.pop();
                        double b = stk.pop();
                        stk.push(a * b);
                    }
                    break;

                    case "/": {
                        double a = stk.pop();
                        double b = stk.pop();
                        stk.push(a / b);
                    }
                    break;

                    case "=": {
                        double a = stk.pop();
                        System.out.println(a);
                    }

                    default:
                    {
                    }
                }
            }
        }
    }
}
