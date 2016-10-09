import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        //Locale.setDefault(new Locale("en", "US"));
        // System.err.println(Locale.getDefault());
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String tmpS ="";
        int sum = 0;
        for(char c:s.toCharArray()){
            if (c!='+'){
                tmpS+=c;
            }else{
                sum += Integer.parseInt(tmpS);
                tmpS = "";
            }
        }
        tmpS = tmpS.substring(0,tmpS.indexOf('.')-1);
        sum += Integer.parseInt(tmpS);
        System.out.println(sum);
    }
}

