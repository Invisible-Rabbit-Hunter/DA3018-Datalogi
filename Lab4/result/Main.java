package se.su.joos1190;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Scanner scanner = getScannerFile("part2/course_data.txt");
        Scanner scanner = null;
        if (args.length >= 1) {
            try {
                scanner = new Scanner(new File(args[0]));
            } catch (FileNotFoundException e) {
                System.err.format("Cannot find file %s.\n", args[0]);
                System.exit(1);
            }
        } else {
            scanner = new Scanner(System.in);
        }
        var heap = new Heap<>(Course.class, 100);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String courseCode = line.substring(0, 6);
            String courseName = line.substring(7);
            Course course = new Course(courseCode, courseName);
            try {
                heap.insert(course);
            } catch (Exception e) {
                System.err.format("Heap is full, cannot insert course %s - %s\n", courseCode, courseName);
                e.printStackTrace();
                System.exit(1);
            }
        }

        try {
            Course least = heap.extractMin();
            System.out.format("%s %s\n", least.code(), least.name());
        } catch (Exception e) {
            System.out.println("Heap is empty, cannot extract minimum");
            e.printStackTrace();
            System.exit(2);
        }
    }
}
