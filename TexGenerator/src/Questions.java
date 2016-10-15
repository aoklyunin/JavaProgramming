import java.io.*;
import java.util.*;

/**
 * Created by aokly on 07.09.2016.
 */
public class Questions {

    public static void generateVariants() {
        List<String> qArr = new ArrayList<>();
        try (FileReader fr = new FileReader("C:\\Users\\aokly\\Dropbox\\школа\\10 класс\\Уроки_ТАУ\\data\\question_in.txt");
             BufferedReader br = new BufferedReader(fr);
             FileWriter writerAnswer = new FileWriter("C:\\Users\\aokly\\Dropbox\\школа\\10 класс\\Уроки_ТАУ\\data\\variants.txt", false)) {
            String str = br.readLine();
            String result = "";
            while (str != null) {
                qArr.add(str);
                str = br.readLine();
            }
            for (int i = 0; i < 20; i++) {
                writerAnswer.append("Вариант " + i + ":" + "\r\n");
                Collections.shuffle(qArr);
                for (int j = 0; j < 4; j++) {
                    writerAnswer.append("   " + qArr.get(j) + "\r\n");
                }
                writerAnswer.append("\r\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}