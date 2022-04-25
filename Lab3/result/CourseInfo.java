package se.su.joos1190;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

///////////////////////////////////////////////
/////////////////   PART 3   //////////////////
///////////////////////////////////////////////


public record CourseInfo(String code, String name, double credits) {
	public static void main(String[] args) {
		Scanner scanner;

		if (args.length >= 1) {
			var file = new File(args[0]);
			try {
				scanner = new Scanner(file).useDelimiter("\\s*([,\\n])\\s*");
			} catch (FileNotFoundException e) {
				System.out.format("File %s was not found!", args[0]);
				System.exit(1);
				return;
			}
		} else {
			scanner = new Scanner(System.in).useDelimiter("\\s*([,\\n])\\s*");
		}


		var courses = new BinarySearchTree<String, CourseInfo>();

		while (scanner.hasNext()) {
			var courseCode = scanner.next();
			var courseName = scanner.next();
			var courseCredits = scanner.nextDouble();
			courses.insert(courseCode, new CourseInfo(courseCode, courseName, courseCredits));
		}

		if (args.length < 2) {
			double creditTotal = 0;
			for (var course : courses) {
				creditTotal += course.getValue().credits;
				System.out.format("%s %-34s %#4.1f\n", course.getValue().code, course.getValue().name, course.getValue().credits);
			}
			System.out.format("Sum%#43.1f\n", creditTotal);
			int n = courses.size();
			System.out.printf("There are %d courses in the database.\n", n);
		} else {
			String courseCode = args[1];
			var course = courses.find(courseCode);
			if (course != null) {
				System.out.format("%s %s   %#4.1f\n", course.getValue().code, course.getValue().name, course.getValue().credits);
			} else {
				System.out.format("Course with code %s does not exist in database!", courseCode);
				System.exit(1);
			}
		}

	}

}
