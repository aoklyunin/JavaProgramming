package com.alexey.samsung;

import org.joda.time.DateTime;

import java.sql.Date;

/**
 * Created by teacher on 20.10.16.
 */
public class Attempt{
    public String mail;
    public String state;
    public String starts;
    public String ends;
    public String tm;
    public double evaluation;
    public String href;
    public double sum;
    public String name;
    public String testName;
    public DateTime addDate;

    public Attempt(String mail, String state, String starts, String ends,
                   String tm, String evaluation, String href, double sum,
                   String name, String testName, DateTime addDate) {
        this.mail = mail;
        this.state = state;
        this.starts = starts;
        this.ends = ends;
        this.tm = tm;
        this.evaluation = evaluation.length()<=1?0:Double.parseDouble(evaluation.replace(",","."));
        this.href = href;
        this.sum = sum;
        this.name = name;
        this.testName = testName;
        this.addDate = addDate;

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