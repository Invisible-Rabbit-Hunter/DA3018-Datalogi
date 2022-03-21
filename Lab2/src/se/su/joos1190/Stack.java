package se.su.joos1190;

import java.util.NoSuchElementException;

public class Stack<E> {
    private final int MIN_CAP = 1<<15;
    private E[] data; // Invariant: MIN_CAP <= data.length
    private int size; // Invariant: 0 <= size <= data.length
    private final Class<E> eClass;

    @SuppressWarnings("unchecked")
    public Stack(Class<E> c) {
        size = 0;
        eClass = c;
        data = (E[]) java.lang.reflect.Array.newInstance(eClass, MIN_CAP);
    }

    @SuppressWarnings("unchecked")
    private void resize(int new_cap) {
        if (new_cap < size) {
            throw new IllegalArgumentException("Illegal argument: new capacity is less than current size");
        }

        E[] new_data = (E[]) java.lang.reflect.Array.newInstance(eClass, MIN_CAP);
        System.arraycopy(data, 0, new_data, 0, size);

        data = new_data;
    }

    public boolean is_empty() {
        return size == 0;
    }

    public void push(E value) {
        if (size == data.length) {
            resize(2*size);
        }
        data[size] = value;
        size++;
    }

    public E pop() {
        if (size == 0) {
            throw new NoSuchElementException("No such element: Trying to pop from empty stack");
        }

        E result = data[size-1];
        size--;

        if (4*size >= data.length) {
            resize(Math.min(data.length>>2, MIN_CAP));
        }

        return result;
    }
}
