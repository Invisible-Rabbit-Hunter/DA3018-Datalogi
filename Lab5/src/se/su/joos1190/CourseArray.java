package se.su.joos1190;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CourseArray {
    public ArrayList<String> courses = new ArrayList<String>(); // Store course names in this attribute


    CourseArray() {
        // Empty, but still needed.
    }

    /*
     * Copy constructor — new object with the same data as 'other'
     */
    CourseArray(CourseArray other) {
        courses.addAll(other.courses);     // Copy items from 'other' to this object
    }

    /*
     * selectionSort – implements the Selection Sort algorithm.
     *
     * Input: none
     * Output: none
     * Side effect: sorts the 'courses' attribute
     * Time complexity: quadratic in the number of elements in 'courses' array.
     */
    public void selectionSort() {
        for (int unsorted = 0; unsorted < courses.size(); unsorted++) {
            int min = unsorted;
            for (int j = unsorted; j < courses.size(); j++) {
                if (courses.get(j).compareTo(courses.get(min)) < 0) {
                    min = j;
                }
            }

            String tmp = courses.get(unsorted);
            courses.set(unsorted, courses.get(min));
            courses.set(min, tmp);
        }
    }

    /*
     * mergeSort - implements the Merge Sort algorithm
     *
     * Input: none
     * Output: none
     * Side effect: sorts the 'courses' attribute
     * Time complexity: O(n lg n) comparisons, where n is the number of elements in the course array.
     */
    public void mergeSort() {
        mergeSort(0, courses.size());
    }

    public void mergeSort(int start, int end) {
        if (end - start <= 1) {
            return;
        }

        int mid = start + (end - start) / 2;
        mergeSort(start, mid);
        mergeSort(mid, end);

        merge(start, mid, end);
    }

    public void merge(int start, int mid, int end) {
        String[] result = new String[end - start];

        int l = 0, h = 0;
        while (l < mid - start && h < end - mid) {
            String x = courses.get(start + l);
            String y = courses.get(mid + h);
            if (x.compareTo(y) < 0) {
                result[l+h] = x;
                l++;
            } else {
                result[l+h] = y;
                h++;
            }
        }

        while (l < mid - start) {
            result[l+h] = courses.get(start + l);
            l++;
        }

        while (h < end - mid) {
            result[l+h] = courses.get(mid + h);
            h++;
        }

        for (int i = start; i < end; i++) {
            courses.set(i, result[i - start]);
        }
    }



    /*
     * javaSort - use Java's library support for sorting.
     *
     * Input: none
     * Output: none
     * Side effect: sorts the 'courses' attribute
     */
    public void javaSort() {
        courses.sort(String::compareTo);
    }

    private static String sharedPrefix(String s1, String s2) {
        int capacity = Math.min(s1.length(), s2.length());
        StringBuilder result = new StringBuilder(capacity);
        for (int i = 0; i < capacity; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                break;
            }

            result.append(s1.charAt(i));
        }

        return result.toString();
    }

    public String longestSharedPrefix() {
        mergeSort();

        String longestPrefix = "";
        for(int i = 0; i < courses.size() - 1; i++) {
            String prefix = sharedPrefix(courses.get(i), courses.get(i+1));
            if (longestPrefix.length() < prefix.length()) {
                longestPrefix = prefix;
            }
        }

        return longestPrefix;
    }

    /*
     * loadData - Convenience function. Reads lines from stdin and put them in 'courses'.
     */
    private void loadData() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            String c = sc.nextLine();
            courses.add(c);
        }
    }
    private void loadData(File file) {
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (sc.hasNext()) {
            String c = sc.nextLine();
            courses.add(c);
        }
    }
    public boolean sorted() {
        for (int i=1; i<courses.size(); i++) {
            if (courses.get(i).compareTo(courses.get(i-1)) < 0) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        // We create 3 CourseArray objects. They contain the same
        // data, but we can apply three different sorting algorithms on them independently.
        CourseArray courses1 = new CourseArray();
        courses1.loadData();    // Read course names from stdin

        CourseArray courses2 = new CourseArray(courses1); // Copy the data to two more arrays using the copy-constructor
        CourseArray courses3 = new CourseArray(courses1);

        long checkpoints[] = new long[4]; // To store timestamps in
        // Start tests
        checkpoints[0] = System.currentTimeMillis();
        courses1.selectionSort();
        checkpoints[1] = System.currentTimeMillis();
        courses2.mergeSort();
        checkpoints[2] = System.currentTimeMillis();
        courses3.javaSort();
        checkpoints[3] = System.currentTimeMillis();

        // Ensure correct sorting
        if (!courses1.sorted()) {
            System.out.println("courses1 is not sorted");
        }
        if (!courses2.sorted()) {
            System.out.println("courses2 is not sorted");
        }
        if (!courses3.sorted()) {
            System.out.println("courses3 is not sorted");
        }

        String algs[] = {"", "selectionSort", "mergeSort", "javaSort"};
        // Output timing results
        for (int i=1; i<4; i++) {
            System.out.format("Time for task %s: %d ms", algs[i], checkpoints[i] - checkpoints[i-1]);
            System.out.println();
        }

        System.out.format("Longest prefix: %s", courses1.longestSharedPrefix());
    }
}
