import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        for (int i = 1; i < 10; i++) {
            System.out.printf("%2d | %5.4f | %8.4f\n",i,(float)1/i,(float)1/(3*i));
        }
    }

}