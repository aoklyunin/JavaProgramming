import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double x = sc.nextDouble();
        boolean a = (x>=-3&&x<=5||x>=9&&x<=15);
        System.out.println(a);




    }
}