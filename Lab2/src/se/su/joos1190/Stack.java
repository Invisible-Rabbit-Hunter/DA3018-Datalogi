package se.su.joos1190;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Stack<E> {
    private final int MIN_CAP = 1<<15;
    private E[] data; // Invariant: MIN_CAP <= data.length
    private int size; // Invariant: 0 <= size <= data.length
    private final Class<E> eClass;

    /**
     * Constructor.
     * @param c the Class instance.
     */
    @SuppressWarnings("unchecked")
    public Stack(Class<E> c) {
        size = 0;
        eClass = c;
        data = (E[]) java.lang.reflect.Array.newInstance(eClass, MIN_CAP);
    }

    /**
     * Resizes the data array to {@code new_cap}.
     * @param new_cap New capacity.
     * @throws IllegalArgumentException if {@code new_cap < size}.
     */
    @SuppressWarnings("unchecked")
    private void resize(int new_cap) {
        if (new_cap < size) {
            throw new IllegalArgumentException("Illegal argument: new capacity is less than current size");
        }

        E[] new_data = (E[]) java.lang.reflect.Array.newInstance(eClass, MIN_CAP);
        System.arraycopy(data, 0, new_data, 0, size);

        data = new_data;
    }

    /**
     * Check if the stack is empty.
     * @return {@code true} if the stack is empty, {@code false} otherwise.
     */
    public boolean is_empty() {
        return size == 0;
    }

    /**
     * Push a value onto the front of the stack.
     * @param value the value to be pushed onto the stack.
     */
    public void push(E value) {
        if (size == data.length) {
            resize(2*size);
        }
        data[size] = value;
        size++;
    }

    /**
     * Pop an element from the front of the stack
     * @return the element removed from the stack
     */
    public E pop() {
        if (size == 0) {
            throw new NoSuchElementException("No such element: Trying to pop from empty stack");
        }

        E result = data[size-1];
        size--;

        if (4*size <= data.length) {
            resize(Math.min(data.length>>2, MIN_CAP));
        }

        return result;
    }

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
