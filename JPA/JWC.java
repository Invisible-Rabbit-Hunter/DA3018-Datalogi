import java.util.Scanner;

public class JWC {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int lines = 0;
    int chars = 0;

    while(sc.hasNext()) {
      var line = sc.nextLine();
      lines += 1;
      chars += line.length();
    }

    System.out.format("%d %d\n", lines, chars);
  }
}
