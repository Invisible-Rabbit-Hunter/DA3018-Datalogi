package se.su.joos1190;

import java.util.Scanner;

public record Course(String code, String name) implements Comparable<Course> {
    @Override
    public int compareTo(Course course) {
        return code.compareTo(course.code);
    }
}
