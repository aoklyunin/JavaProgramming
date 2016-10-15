import java.util.Arrays;
import java.util.Random;

/**
 * Created by aokly on 06.09.2016.
 */
public class TexStruct {

    public static final String TEX_BEGIN = "\\documentclass{article}\r\n" +
            "\\usepackage[utf8]{inputenc}\r\n" +
            "\\usepackage[english, russian]{babel}\r\n" +
            "\\usepackage{amsmath}\r\n" +
            "\\begin{document}";
    public static final String TEX_END = "\\end{document}";

    // НОД
    int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    // НОК
    int lcm(int a, int b) {
        return a / gcd(a, b) * b;
    }


    private int[] mKArr;
    private int[] mDArr;
    private int mLn;
    private int mType;
    public static int X_TYPE = 0;
    public static int P_TYPE = 1;

    static String multStr(TexStruct a, TexStruct b) {
        return "(" + a.toTexString() + ")(" + b.toTexString() + ")";
    }

    TexStruct(int mType) {
        Random r = new Random();
        mLn = (byte) (r.nextInt(5) + 8);
        //mLn = 5;
        mKArr = new int[mLn];
        mDArr = new int[mLn];
        for (int i = 1; i < mLn; i++) {
            mKArr[i] = (r.nextInt(10) - 5);
        }
        mKArr[0] = (r.nextInt(10) - 5);
        Arrays.fill(mDArr, 1);
        this.mType = mType;
    }

    public TexStruct(int ln, int type) {
        mLn = ln;
        mType = type;
        mKArr = new int[mLn];
        mDArr = new int[mLn];
        Arrays.fill(mDArr, 1);
    }

    public TexStruct(int[] KArr, int ln, int type) {
        mKArr = KArr;
        mDArr = new int[KArr.length];
        Arrays.fill(mDArr, 1);
        mLn = ln;
        mType = type;
    }

    public TexStruct(TexStruct tx) {
        this.mLn = tx.getLn();
        if (tx.getKArr() != null)
            this.mKArr = Arrays.copyOf(tx.getKArr(), mLn);
        else
            this.mKArr = null;
        if (tx.getDArr() != null)
            this.mDArr = Arrays.copyOf(tx.getDArr(), mLn);
        else
            this.mDArr = null;

        this.mType = tx.getType();
    }

    public static TexStruct getIndentStruct(int type) {
        int[] arr = {1};
        return new TexStruct(arr, 1, type);
    }

    @Override
    public String toString() {
        String s = "[ ";
        for (int i = 0; i < mLn; i++) {
            s += mKArr[i];
            if (mDArr[i] != 1)
                s += "/" + mDArr[i];
            s += " ";
        }
        return s + "]";
    }

    public String toTexString() {
        String s = "";
        if (mLn <= 0) {
            return "0";
        } else {
            if (mLn == 1) {
                if (mDArr[0] != 1) {
                    return "\\frac{" + mKArr[0] + "}{" + mDArr[0] + "}";
                } else
                    return mKArr[0] + "";
            }
        }
        char c;
        if (mType == X_TYPE)
            c = 't';
        else
            c = 'p';
        for (int i = 0; i < mLn; i++) {
            if (mKArr[i] != 0) {
                if (i != 0) {
                    if (mKArr[i] > 0) {
                        s += '+';
                    }
                }
                if ((mKArr[i] != 1 && mKArr[i] != -1) || i == mLn - 1)
                    if (mDArr[i] == 1)
                        s += mKArr[i] + "";
                    else if (mKArr[i] > 0)
                        s += "\\frac{" + mKArr[i] + "}{" + mDArr[i] + "}";
                    else
                        s += "-\\frac{" + (-mKArr[i]) + "}{" + mDArr[i] + "}";
                else if (mKArr[i] == -1) s += "-";
                if (i == mLn - 2) {
                    s += c;
                } else {
                    if (i != mLn - 1)
                        s += "" + c + '^' + "{" + (mLn - i - 1)+"}";
                }
            }
        }
        if (s != "")
            return (s.charAt(0) == '+') ? s.substring(1) : s;
        else
            return s;
    }

    public TexStruct diff() {
        TexStruct tx;
        if (mLn <= 1) {
            int tmp[] = {0};
            return new TexStruct(tmp, 1, mType);
        } else {
            int arr[] = new int[mLn - 1];
            for (int i = 0; i < mLn - 1; i++) {
                arr[i] = (mKArr[i] * (mLn - i - 1));
            }
            return new TexStruct(arr, (mLn - 1), mType);
        }
    }

    public TexStruct diff(int n) {
        if (n <= 1) {
            return diff();
        } else {
            return diff(n - 1).diff();
        }
    }

    public int[] getKArr() {
        return mKArr;
    }

    public int[] getDArr() {
        return mDArr;
    }

    public int getLn() {
        return mLn;
    }

    public int getType() {
        return mType;
    }

    public static TexStruct maxTx(TexStruct a, TexStruct b) {
        if (a.getLn() > b.getLn()) {
            return new TexStruct(a);
        } else {
            return new TexStruct(b);
        }
    }

    public static TexStruct minTx(TexStruct a, TexStruct b) {
        if (a.getLn() <= b.getLn()) {
            return new TexStruct(a);
        } else {
            return new TexStruct(b);
        }
    }

    public TexStruct sum(TexStruct tx) {
        TexStruct resTx = maxTx(tx, this);
        TexStruct addTx = minTx(tx, this);
        int resArr[] = resTx.getKArr();
        int addArr[] = addTx.getKArr();
        int addLn = addTx.getLn();
        int resLn = resTx.getLn();
        for (int i = 0; i < addLn; i++) {
            resArr[resLn - i - 1] += addArr[addLn - i - 1];
        }
        return resTx;
    }

    public void reduce() {
        for (int i = 0; i < mLn; i++) {
            int nod = gcd(mDArr[i], mKArr[i]);
            if (nod != 0) {
                mDArr[i] /= nod;
                mKArr[i] /= nod;
                if (mDArr[i] < 0) {
                    mDArr[i] = -mDArr[i];
                    mKArr[i] = -mKArr[i];
                }
            }
        }

    }

    public void shrink() {
        while (mLn >= 1 && mKArr[0] == 0) {
            mLn--;
            int arr[] = new int[mLn];
            for (int i = 0; i < mLn - 1; i++) {
                arr[i] = mKArr[i + 1];
            }
            mKArr = arr;
        }
    }


    public TexStruct mult(float a) {
        TexStruct resTx = new TexStruct(this);
        int[] resArr = resTx.getKArr();
        for (int i = 0; i < mLn; i++) {
            resArr[i] *= a;
        }
        resTx.reduce();
        return resTx;
    }

    public TexStruct diff(TexStruct tx) {
        TexStruct resTx = new TexStruct(mLn, mType);
        int inArr[] = tx.getKArr();

        for (int i = tx.getLn() - 1; i >= 0; i--) {
            TexStruct tmpTx;
            if (inArr[i] != 0) {
                tmpTx = new TexStruct(this.diff(tx.getLn() - i - 1));
            } else {
                int[] tmp = {0};
                tmpTx = new TexStruct(tmp, 0, X_TYPE);
            }
            TexStruct mTx = tmpTx.mult(inArr[i]);
            resTx = resTx.sum(mTx);
        }
        return resTx;
    }


    public boolean isExist() {
        return mLn > 0;
    }

    public static String getQuestionString(TexStruct a, TexStruct b) {
        return "Найти значение выражения $y(t) = x(t)b(p)$, где \n" +
                "\n" +
                "$x(t)=" + a.toTexString() + "$; \r\n\r\n$b(p) = " + b.toTexString() + "$ (p - оператор дифференцирования) \r\n\r\n";
    }

    public String diffPolynom(TexStruct tx, boolean flgDiscuss) {
        TexStruct resTx = new TexStruct(mLn, mType);
        String str = "";
        if (flgDiscuss) {
            str = getQuestionString(this, tx);
        }
        str += "Возьмём все необходимые производные от x(t):\r\n\r\n";

        int inArr[] = tx.getKArr();
        TexStruct[] txArr = new TexStruct[tx.getLn()];

        for (int i = tx.getLn() - 1; i >= 0; i--) {
            TexStruct tmpTx = new TexStruct(this.diff(tx.getLn() - i - 1));
            if (i != tx.getLn() - 1) {
                str += "$p";
                if (i != tx.getLn() - 2)
                    str += "^" + (tx.getLn() - i - 1);
                str += "x(t)=" + tmpTx.toTexString() + "$\r\n\r\n";
            }
            if (inArr[i] != 0) {
                txArr[i] = tmpTx;
            } else {
                int[] tmp = {0};
                txArr[i] = new TexStruct(tmp, 0, X_TYPE);
            }
        }
        str += "Подставим найденные значения:\r\n\r\n$x(t)b(p) = ";
        for (int i = 0; i < tx.getLn(); i++) {
            if (txArr[i].isExist()) {
                str += "(" + txArr[i].toTexString() + ")*";
                if (inArr[i] < 0) {
                    str += "(" + inArr[i] + ")";
                } else {
                    str += inArr[i];
                }
                if (i != tx.getLn() - 1) str += "+";
                TexStruct mTx = txArr[i].mult(inArr[i]);
                resTx = resTx.sum(mTx);
            }

        }
        resTx.shrink();

        System.out.println(resTx + " " + resTx.getLn());
        str += "=" + resTx.toTexString() + "$\r\n\r\n";
        if (flgDiscuss)
            str += "Ответ: $" + resTx.toTexString() + "$\r\n\r\n";
        return str;
    }

    public void devide(int pos, int val) {
        mDArr[pos] *= val;
    }


    public TexStruct integrate(int y0) {
        int[] arr = new int[mLn + 1];
        for (int i = 0; i < mLn; i++) {
            arr[i] = mKArr[i];
        }
        arr[mLn] = 1;
        TexStruct tx = new TexStruct(arr, mLn + 1, mType);
        for (int i = 0; i < mLn; i++) {
            tx.devide(i, (tx.getLn() - i));
        }
        tx.reduce();
        arr[mLn] = y0;
        return tx;
    }

}
