package com.alexey.samsung;

/**
 * Created by teacher on 20.10.16.
 */
public class Attempt{
    String mail;
    String state;
    String starts;
    String ends;
    String tm;
    double evaluation;
    String href;
    double sum;
    String name;

    public Attempt(String mail, String state, String starts, String ends, String tm, String evaluation, String href, double sum, String name) {
        this.mail = mail;
        this.state = state;
        this.starts = starts;
        this.ends = ends;
        this.tm = tm;
        this.evaluation = evaluation.length()<=1?0:Double.parseDouble(evaluation.replace(",","."));
        this.href = href;
        this.sum = sum;
        this.name = name;
    }



    @Override
    public String toString() {
        return "name: " + name+" | "+
                "p: " +  evaluation + " |" +
                "sum: " + sum + "|" +
                ", href='" + href + '\'';
    }

    public int compareTo(Attempt a) {
        if (this.evaluation>0)
            return Double.compare(this.evaluation,a.evaluation);
        else if (a.evaluation>0) return -1;
        else return Double.compare(this.sum,a.sum);
    }

}