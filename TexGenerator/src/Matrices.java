import java.util.Arrays;
import java.util.Random;

/**
 * Created by teacher on 09.10.16.
 */
public class Matrices {
    int n;
    int m;
    double arr[][];

    Matrices(double [][] arr){
        this.arr = arr;
        n = this.arr.length;
        m = this.arr[0].length;
    }

    Matrices(int ln){
        Random r = new Random();
        arr = new double[ln][ln];
        for (int i=0;i<ln;i++){
            for (int j = 0; j < ln; j++) {
                arr[i][j] = (double)(r.nextInt(10)-5);
            }
        }
        n = ln;
        m= ln;
    }


    public String toTexString(){
        String s = "\\begin{pmatrix}\n";
        for (int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                s+= (int)arr[i][j]+" & ";
            }
            s = s.substring(0,s.length()-2);
            s+="\\\\\n";
        }
        s += "\\end{pmatrix}\n";
        return s;
    }
}
