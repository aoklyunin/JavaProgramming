import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class FirstClass {

    static TexStruct tx;
    static TexStruct tx2;

    public static void main(String[] args) {
        try (FileWriter writerAsk = new FileWriter("C:\\Users\\aokly\\Dropbox\\школа\\10 класс\\Уроки_ТАУ\\tmp\\ask.tex", false);
             FileWriter writerAnswer = new FileWriter("C:\\Users\\aokly\\Dropbox\\школа\\10 класс\\Уроки_ТАУ\\tmp\\answer.tex", false)) {
            writerAsk.write(TexStruct.TEX_BEGIN + "\r\n\r\n");
            writerAnswer.write(TexStruct.TEX_BEGIN + "\r\n\r\n");
            for (int i = 1; i < 100; i++) {
                tx = new TexStruct(TexStruct.X_TYPE);
                tx2 = new TexStruct(TexStruct.P_TYPE);
                Random r = new Random();
                TexStruct yTx;
                if (r.nextInt(2) == 1) {
                    int[] arr = {1, 0};
                    yTx = new TexStruct(arr, 2, TexStruct.P_TYPE);
                } else {
                    yTx = TexStruct.getIndentStruct(TexStruct.P_TYPE);
                }
                TransferFunction tf = new TransferFunction(tx2, yTx);
                int y0 = r.nextInt(20) - 10;
                writerAnswer.write("\\section{Вариант}\r\n\r\n");
                System.out.println(i);
                // writerAnswer.write("$" + tx.toTexString() + "$" + "\r\n\r\n");
                // writerAnswer.write("$" + tf.toTexString() + "$" + "\r\n\r\n");
                writerAnswer.write(TransferFunction.solveTf(tx, tf, y0));
                writerAsk.write("\\section{Вариант}\r\n\r\n");
                writerAsk.write(TransferFunction.getQuestion(tx,tf,y0)+"\r\n\r\n");
            }
            writerAnswer.write(TexStruct.TEX_END);
            writerAsk.write(TexStruct.TEX_END);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fuck");
        }


        // Questions.generateVariants();
    }
}