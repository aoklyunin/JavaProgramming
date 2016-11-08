package com.example.alex;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by teacher on 08.11.16.
 */
public class SettingsClass {
    public static String pass= "qwerty123";

    public Date d;
    public int nCicle;
    public int minD1,maxD1,minD2,maxD2,minD3,maxD3,minL1,maxL1,minL2,maxL2;
    public int maxPress;

    public SettingsClass(){
        d = new Date();
    };
    public SettingsClass(String path){
        loadFromFile(path);
    }

    public void loadFromFile(String path){
        try(Scanner in = new Scanner(new FileInputStream(path)))   {
            String s = in.nextLine();
            SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
            try {
                d = sm.parse(s);
            } catch (ParseException e) {
                System.out.println("Ошибка чтения");
                d = new Date();
            }
            nCicle = in.nextInt();
            maxPress = in.nextInt();
            minD1 = in.nextInt();
            maxD1 = in.nextInt();
            minD2 = in.nextInt();
            maxD2 = in.nextInt();
            minD3 = in.nextInt();
            maxD3 = in.nextInt();
            minL1 = in.nextInt();
            maxL1 = in.nextInt();
            minL2 = in.nextInt();
            maxL2 = in.nextInt();

        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

    }

    public void save(String path){
        try(FileWriter writer = new FileWriter(path, false)){
            SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
            // myDate is the java.util.Date in yyyy-mm-dd format
            // Converting it into String using formatter
            writer.write(sm.format(d)+"\n");
            writer.write(nCicle+"\n");
            writer.write(maxPress+"\n");
            writer.write(minD1+" "+ maxD1+"\n");
            writer.write(minD2+" "+ maxD2+"\n");
            writer.write(minD3+" "+ maxD3+"\n");
            writer.write(minL1+" "+ maxL1+"\n");
            writer.write(minL2+" "+ maxL2+"\n");
            writer.flush();
        } catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public int l1,l2,d1,d2,d3;
    public int t, p;

    public void setTempVals(){
        l1 = 1100;
        l2 = 1200;
        d1 = 1300;
        d2 = 1400;
        d3 = 1500;
        t = 28;
        p = 450;
    }
}
