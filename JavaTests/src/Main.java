import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        //Locale.setDefault(new Locale("en", "US"));
        // System.err.println(Locale.getDefault());
        String regStr = "[0-9]";
        String inS="aasd dfnsjfsd   asndj    asdasdas  ";
        String [] sArr = inS.split(" ");
        for(String s:sArr){
            System.out.println(s);
        }
    }
}

