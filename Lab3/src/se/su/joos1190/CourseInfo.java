package se.su.joos1190;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CourseInfo {

	public static void main(String[] args) {
		var file = new File("courses.csv");
		Scanner scanner;
		try {
			scanner = new Scanner(file).useDelimiter("\\s*(,|\\n)\\s*");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}


		BinarySearchTree courses = new BinarySearchTree();

		while (scanner.hasNext()) {
			var courseCode = scanner.next();
			var courseName = scanner.next();
			var courseCredits = scanner.nextDouble();
			courses.insert(courseCode, courseName, courseCredits);
		}
		int n = courses.size();
		System.out.printf("There are %d courses in the database.\n", n);
		
		String name = courses.find("MM2001").getCourseName();
		System.out.printf("Name: %s\n", name);
		courses.remove("DA3018");
		name = courses.find("DA4001").getCourseName();
		System.out.printf("Name: %s\n", name);
		courses.remove("DA4002");
		name = courses.find("DA4001").getCourseName();
		System.out.printf("Name: %s\n", name);
		courses.remove("MM2001");
		name = courses.find("MM5016").getCourseName();
		System.out.printf("Name: %s\n", name);
		name = courses.find("MM2001").getCourseName();
		System.out.printf("Name: %s\n", name);
	}

}
