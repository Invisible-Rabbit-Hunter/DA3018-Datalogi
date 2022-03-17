package se.su.joos1190;

import java.util.Scanner;

public class Course implements Comparable<Course> {
    private String code;
    private String name;

    public Course(String courseCode, String courseName) {
        code = courseCode;
        name = courseName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Course course) {
        return code.compareTo(course.code);
    }
}
