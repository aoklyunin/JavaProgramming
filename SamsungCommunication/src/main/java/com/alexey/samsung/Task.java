package com.alexey.samsung;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by teacher on 20.10.16.
 */
public class Task {
    public int t_type;
    public int w_type;
    public Date date;
    public String [] ests;
    public String name;

    public Task(int t_type, int w_type, Date date, String[] ests, String name) {
        this.t_type = t_type;
        this.w_type = w_type;
        this.date = date;
        this.ests = ests;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Task{" +
                "date=" + date +
                ", name='" + name + '\'' +
                '}';
    }

    public static List<String> getTaskNameList(ArrayList<Task> alst){
        List<String>lst = new ArrayList<>();
        for(Task at:alst){
            lst.add(at.name);
        }
        return lst;
    }
}
