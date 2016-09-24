package com.java.sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by aokly on 24.09.2016.
 */
public class Operations {
    public static void checkGroup(){
        HashSet<String> hs = new HashSet<>();
        try (FileReader rG = new FileReader("source\\group.txt");
             FileReader rK = new FileReader("source\\konf.txt");
             BufferedReader bG = new BufferedReader(rG);
             BufferedReader bK = new BufferedReader(rK)) {

            String commandstring;
            while ((commandstring = bG.readLine()) != null) {
                if (commandstring.contains(" ") )hs.add(commandstring);
            }
            while ((commandstring = bK.readLine()) != null) {
               if(!hs.contains(commandstring))System.out.println(commandstring);
               // hs.add(commandstring);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fuck");
        }
    }

    public static void simpleVoid(){
        try (FileReader fileReader = new FileReader("source\\form.csv");
             FileWriter fileWriter = new FileWriter("out\\tmp.txt", false);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            fileWriter.write("dfhdfh\r\n\r\n");

            String commandstring;
            while ((commandstring = bufferedReader.readLine()) != null) {
                //System.out.println(commandstring);
                String ls_regex = "\".*?\"";
                Pattern pattern = Pattern.compile(ls_regex);
                Matcher matcher = pattern.matcher(commandstring);
                while (matcher.find()) {
                    String tmp = matcher.group();
                    System.out.println(tmp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fuck");
        }
        System.out.println("eagasg");
        // Questions.generateVariants();
    }
}
