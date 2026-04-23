import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();

        String[] words = s.trim().split("\\s+");

        int min = words[0].length();
        int max = words[0].length();

        for (int i = 0; i < words.length; i++) {
            int len = words[i].length();

            if (len < min) {
                min = len;
            }
            if (len > max) {
                max = len;
            }
        }
        System.out.println("Найкоротше слово: " + min);
        System.out.println("Найдовше слово: " + max);
    }
}