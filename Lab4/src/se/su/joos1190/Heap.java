/*
 * Implement a min heap
 */
package se.su.joos1190;

import java.lang.Exception;

public class Heap<E extends Comparable<E>> {                    // Note: just the skeleton of a class!
    private E[] heap_array;
    private int n_elems = 0;
    private int capacity;

    /**
     * Constructor
     * Note how we no longer use the simple Java array creation.
     * That is not possible with generics, for technical reasons.
     *
     * The tricks below causes compiler warnings, which are suppressed here
     * because they are planned (in some  sense) and supported by common
     * Java practice.
     */
    @SuppressWarnings("unchecked")
    Heap(Class<E> c, int _capacity) {
        capacity = _capacity;
        heap_array = (E[]) java.lang.reflect.Array.newInstance(c, capacity+1);
    }
    /*
        i = 2
        parent(i) = 0 = floor((2 + 1) / 2) - 1 = 1 - 1 = 0
        left(i) = 5
        right(i) = 6

        i = 1
        parent(i) = 0
        left(i) = 3
        right(i) = 4
     */

    private static int left(int i) {
        return (i << 1) + 1;
    }

    private static int right(int i) {
        return (i << 1) + 2;
    }

    private static int parent(int i) {
        return i == 0 ? 0 : (i - 1) >> 1;
    }

    /**
     * Private method for maintaining the heap.
     *
     * @param i, index of the element to heapify from
     */
    private void heapify(int i) {
        int l = left(i);
        int r = right(i);
        int least;
        if (l <= n_elems && heap_array[l].compareTo(heap_array[i]) < 0) {
            least = l;
        } else {
            least = i;
        }

        if (r <= n_elems && heap_array[r].compareTo(heap_array[least]) < 0) {
            least = r;
        }

        if (least != i) {
            swap(i, least);
            heapify(least);
        }
    }

    public int capacity() {
        return capacity;
    }

    public int size() {
        return n_elems;
    }

    public boolean isEmpty() {
        return n_elems == 0;
    }

    private void swap(int i, int j) {
        E tmp = heap_array[i];
        heap_array[i] = heap_array[j];
        heap_array[j] = tmp;
    }

    /**
     * Add an element to the heap and ensure the heap property
     * Throws an exception if trying to add elements to a full heap.
     *
     * @param x Element to add
     */
    public void insert(E x) throws Exception {
        if (n_elems >= capacity) {
            throw new Exception("capacity reached");
        }

        heap_array[n_elems] = x;

        int i = n_elems;
        while (i > 1 && heap_array[parent(i)].compareTo(heap_array[i]) > 0) {
            swap(i, parent(i));
            i = parent(i);
        }
        n_elems++;
    }

    /**
     * Remove and return smallest element, and maintain the heap property.
     * Throws an exception if trying to extract an element from an empty heap.
     */
    public E extractMin() throws Exception {
        if (n_elems <= 0) {
            throw new Exception("heap underflow");
        }
        E min = heap_array[0];
        n_elems--;
        heap_array[0] = heap_array[n_elems];
        heapify(0);
        return min;
    }

    /**
     * For convenience, a small program to test the code.
     * There are better ways of doing this kind of testing. See `junit`!
     */
    static public void main(String args[]) { // A simple test program
        // Declare two heaps. Both should work nicely!
        // This time around, we store doubles instead of ints in one of the heaps.
        // Notice that we use wrapper classes Double and Integer instead of the primitive type double.
        // Java's primitive types have these wrappers for when a class is needed.
        Heap<Double> h1 = new Heap<Double>(Double.class, 100);
        Heap<Integer> h2 = new Heap<Integer>(Integer.class, 10);
        int data[] = {1, 4, 10, 14, 7, 9, 3, 2, 8, 16};


        //
        // Insert 1 element to heap 1, and several to heap 2.
        //
        System.out.println("Inserting data.");
        try {
            h1.insert(7.0);       // Insert a single element in heap 1

            // Insert several elements in heap 2. Heap 1 must not be affected.
            for (int x: data) {
                h2.insert(x);
            }
        } catch (Exception e) {
            System.err.println("During insertion:");
            System.err.println(e);
            System.exit(1);
        }

        if (h2.size() != data.length) {
            System.err.println("Error! Wrong number of elements in heap 2.");
        }


        //
        // Time to empty heap 2. Does that work?
        //
        try {
            System.out.println("Contents of heap 2, should come out sorted:");
            while (! h2.isEmpty()) {
                int x = h2.extractMin();
                System.out.println(x);
            }
            if (! h2.isEmpty()) {
                System.err.println("Error! Heap 2 has not been emptied!");
            }
            if (h1.size() != 1) {
                System.err.println("Error! Wrong number of elements in heap 1.");
            }

        } catch (Exception e) {
            System.err.println("During extraction:");
            System.err.println(e);
            System.exit(1);
        }
    }
}
