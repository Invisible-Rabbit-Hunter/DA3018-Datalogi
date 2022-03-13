package se.su.joos1190;

public class Stack {
    private final int MIN_CAP = 1<<15;
    private double[] data; // Invariant: MIN_CAP <= data.length
    private int size; // Invariant: 0 <= size <= data.length

    public Stack() {
        size = 0;
        data = new double[MIN_CAP];
    }

    private void resize(int new_cap) {
        new_cap = Math.min(new_cap, MIN_CAP);

        if (new_cap < size) {
            // TODO: Replace with more precise exception.
            throw new UnsupportedOperationException();
        }

        double[] new_data = new double[new_cap];
        System.arraycopy(data, 0, new_data, 0, size);

        data = new_data;
    }

    public boolean is_empty() {
        return size == 0;
    }

    public void push(double value) {
        if (size == data.length) {
            resize(2*size);
        }
        data[size] = value;
        size++;
    }

    public double pop() {
        if (size == 0) {
            // TODO: Replace with more precise exception.
            throw new UnsupportedOperationException();
        }

        double result = data[size-1];
        size--;

        if (2*size >= data.length) {
            resize(size);
        }

        return result;
    }
}
