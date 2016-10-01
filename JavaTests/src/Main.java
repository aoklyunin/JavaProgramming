import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));
       // System.err.println(Locale.getDefault());

        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 5; i++) {
            double a = sc.nextDouble();
            double b = sc.nextDouble();

            //int b = sc.nextInt();
            //double ln = Math.sqrt(a * a + b * b);
            System.out.println(a+b);
            System.out.println(a-b);
            System.out.println(a*b);
            System.out.println(a/b);
            System.out.println(Math.pow(a,b));
        }


    }


}

