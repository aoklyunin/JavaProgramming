package com.alexey.samsung;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by aokly on 29.09.2016.
 */
public class CustomOperations {

    public static void registerUserMdl() throws InterruptedException {
        WebSelenium  webSelenium = new WebSelenium();
        webSelenium.loginToMdl();
        Thread.sleep(1000);

        DBHelper dbHelper = new DBHelper();
        try {
            dbHelper.connect();
            ArrayList<ArrayList<String>>  lst = dbHelper.getValsRegister();
            for (ArrayList<String> ls:lst){
                webSelenium.fillAddUserPage(ls);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //fillPage("asf1","asfasf2","asfas fa2","asasfasf@asg.rt");

    }
    // преобразование разных записей vk в id
    public static void modifyVK() throws SQLException, ClassNotFoundException {
        VkApi vk = new VkApi();
        DBHelper dbHelper = new DBHelper();
        dbHelper.connect();
        ArrayList<HashMap<String, String>> lst = dbHelper.getStudentRecs();
        for(HashMap<String, String> hm:lst){
            String inStr = hm.get(DBHelper.KEY_VK);
            String vkVal = inStr.replace("http://","");
            vkVal = vkVal.replace("https://","");
            vkVal = vkVal.replace("m.vk.com/","");
            vkVal = vkVal.replace("vk.com/","");
            vkVal = vkVal.replace("Vk.com/","");
            try {
                String vkJSON = vk.getUserInform(vkVal);
                JSONObject obj = new JSONObject(vkJSON);
                if (obj.has("response")) {
                    JSONArray arr = obj.getJSONArray("response");
                    JSONObject o = arr.getJSONObject(0);
                    //String idStr = getString("id");
                    String newVkStr = o.get("id")+"";
                    dbHelper.updateRecord(DBHelper.TABLE_SCOOLERS,
                            DBHelper.KEY_VK + "="+ DBHelper.toSQLString(inStr),
                            DBHelper.KEY_VK+"="+DBHelper.toSQLString(newVkStr));
                    System.out.println(newVkStr);
                }else{
                    System.out.println(vkJSON);
                }
                Thread.sleep(300);
            } catch (IOException e) {
                System.out.println(e);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // получаем список id тех, кто не заполнил гугл форму
    public static void getUnregistredYsersFromConf() throws SQLException, ClassNotFoundException {
        VkApi vk = new VkApi();
        DBHelper dbHelper = new DBHelper();
        dbHelper.connect();
        try {
            String req = vk.getUsersFromGroup("109197373");
            JSONObject obj = new JSONObject(req);
            if (obj.has("response")) {
                JSONObject o =  obj.getJSONObject("response");
                JSONArray arr = o.getJSONArray("items");
                for (Object jo:arr){
                    if (!dbHelper.chechStudentRecordByVK((int)jo+"")){
                        System.out.println("http://vk.com/id"+(int)jo);
                    }
                }
            //    System.out.println(arr);
            }else{
                System.out.println(req);
            }
            //dbHelper.chechStudentRecordByMail()
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void getAttemps() throws Exception {
        try(WebSelenium webSelenium = new WebSelenium()) {
            webSelenium.loginToMdl();
            Thread.sleep(1000);
            webSelenium.getAttempts("02_Ведение в Java");
        }
    }

    public static void copyQuestions()throws Exception{
        try(WebSelenium webSelenium = new WebSelenium()) {
            webSelenium.loginToMdl();
            Thread.sleep(1000);
            //webSelenium.copyQuestion("558,1");
            String arr [] = {
                    "564,1",
                    "565,1",
                    "566,1",
                    "116,1",
                    "117,1",
                    "118,1",
                    "119,1",
                    "120,1",
                    "28,1",
                    "29,1",
                    "144,1",
                    "20,1",
                    "21,1"
            };
            for (String a:arr) {
                //webSelenium.copyQuestion(a);
                //Thread.sleep(500);
                //webSelenium.moveCopiedQuestions(a, "1129,33", (n) -> n % 2 == 0);
                Thread.sleep(500);
            }
        }
    }
}


