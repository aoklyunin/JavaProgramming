/**
 * Created by aokly on 11.09.2016.
 */
class TransferFunction {
    TexStruct mNum, mDen;

    public TransferFunction() {
        mNum = TexStruct.getIndentStruct(TexStruct.P_TYPE);
        mDen = TexStruct.getIndentStruct(TexStruct.P_TYPE);
    }

    public TransferFunction(TexStruct num, TexStruct den) {
        mNum = num;
        mDen = den;
    }

    public boolean isSimple() {
        return mDen.getLn() == 1;
    }

    public String toString() {
        return "(" + mNum + ")" + "/(" + mDen + ")";
    }

    public String toTexString() {
        return "\\frac{" + mNum.toTexString() + "}{" + mDen.toTexString() + "}";
    }

    public TexStruct getNum() {
        return mNum;
    }

    public TexStruct getDen() {
        return mDen;
    }

    public static String getQuestion(TexStruct tx, TransferFunction tf, int y0) {
        String s = "Дана передаточная ф-ия $W(p)=" + tf.toTexString() + "$, требуется найти $y(t)$ при $x(t)=" +
                tx.toTexString() + "$";
        if (!tf.isSimple()) {
            s += ", $y(0)=" + y0 + "$\r\n\r\n";
        } else {
            s += "\r\n\r\n";
        }
        return s;
    }

    public static String solveTf(TexStruct tx, TransferFunction tf, int y0) {
        String s = getQuestion(tx,tf,y0);
        s += "Перепишем выражение в виде $y(t)a(p)=x(t)b(p)$ :\r\n\r\n" +
                "$y(t)(" + tf.getDen().toTexString() + ")=x(t)(" + tf.getNum().toTexString() + ")$";
        s += "\r\n\r\nНайдём $x^*=x(t)b(p)$:\r\n\r\n" + tx.diffPolynom(tf.getNum(), false) + "\r\n\r\n";
        TexStruct txStar = tx.diff(tf.getNum());
        // System.out.println(tx);
        //  System.out.println(tf.getNum());
        //   System.out.println(txStar);
        s += "\r\n\r\n";
        if (!tf.isSimple()) {
            s += "Найдём первообразную x*:\r\n\r\n";
            String integratedFull = txStar.integrate(y0).toTexString();
            System.out.println(integratedFull);
            String integrated = "";
            if (integratedFull.lastIndexOf("+") > integratedFull.lastIndexOf("-")) {
                integrated = integratedFull.substring(0, integratedFull.lastIndexOf("+") - 1);
            } else {
                integrated = integratedFull.substring(0, integratedFull.lastIndexOf("-") - 1);
            }
            s += "$" + integrated + "+C.$\r\n\r\n" +
                    "Т.к. $y(0)=" + y0 + "$, то $C=" + y0 + "$, а, значит, \r\n\r\n" +
                    "$y(t)=" + integratedFull + "$\r\n\r\n";
            s += "Ответ: $y(t) = " + integratedFull + "$";

        } else {
            txStar.shrink();
            String txStr = txStar.toTexString();
            s += "$y(t)=x^*=" +  txStr+ "$\r\n\r\n" +
                    "Ответ: $y(t) = " + txStr + "$";
        }
        return s + "\r\n\r\n";
    }


}
