
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random r = new Random();
        try (FileWriter writerAnswer = new FileWriter("variants.txt", false)) {
            writerAnswer.write(TexStruct.TEX_BEGIN + "\r\n\r\n");
            for (int l = 0; l < 100; l++) {
                String resS = "\\section{}\n$";
                int ln = 3;
                for (int i = 0; i < 3; i++) {
                    int k = 0;
                    while (k == 0)
                        k = r.nextInt(10) - 5;
                    if (k>0){
                        if(i!=0)
                            resS+="+";
                        if (k!=1){
                            resS+=k;
                        }
                    }else{
                        if(k!=-1){
                            resS += k;
                        }else{
                            resS += "-";
                        }
                    }
                    Matrices m = new Matrices(ln);
                    resS += m.toTexString();
                }
                writerAnswer.write(resS+"$\n");
            }
            writerAnswer.write(TexStruct.TEX_END + "\r\n\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}