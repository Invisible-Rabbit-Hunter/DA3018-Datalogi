package se.su.joos1190;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CourseInfo {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Error: A file must be provided.");
			System.exit(1);
		}

		var file = new File(args[0]);
		Scanner scanner;
		try {
			scanner = new Scanner(file).useDelimiter("\\s*([,\\n])\\s*");
		} catch (FileNotFoundException e) {
			System.out.format("File %s was not found!", args[0]);
			System.exit(1);
			return;
		}

		BinarySearchTree courses = new BinarySearchTree();

		while (scanner.hasNext()) {
			var courseCode = scanner.next();
			var courseName = scanner.next();
			var courseCredits = scanner.nextDouble();
			courses.insert(courseCode, courseName, courseCredits);
		}

		if (args.length < 2) {
			double creditTotal = 0;
			for (BinarySearchTree.BSTNode course : courses) {
				creditTotal += course.getCredits();
				System.out.format("%s %-34s %#4.1f\n", course.getCourseCode(), course.getCourseName(), course.getCredits());
			}
			System.out.format("Summa%#41.1f\n", creditTotal);
			int n = courses.size();
			System.out.printf("There are %d courses in the database.\n", n);
		} else {
			String courseCode = args[1];
			BinarySearchTree.BSTNode course = courses.find(courseCode);
			if (course != null) {
				System.out.format("%s %s   %#4.1f\n", course.getCourseCode(), course.getCourseName(), course.getCredits());
			} else {
				System.out.format("Course with code %s does not exist in database!", courseCode);
				System.exit(1);
			}
		}

	}

}
