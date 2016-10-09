package com.alexey.samsung;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by aokly on 29.09.2016.
 */
public class CustomOperations {

    public static void registerUserMdl() throws InterruptedException {
        WebSelenium webSelenium = new WebSelenium();
        webSelenium.loginToMdl();
        Thread.sleep(1000);

        DBHelper dbHelper = new DBHelper();
        try {
            dbHelper.connect();
            ArrayList<ArrayList<String>> lst = dbHelper.getValsRegister();
            for (ArrayList<String> ls : lst) {
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
        for (HashMap<String, String> hm : lst) {
            String inStr = hm.get(DBHelper.KEY_VK);
            String vkVal = inStr.replace("http://", "");
            vkVal = vkVal.replace("https://", "");
            vkVal = vkVal.replace("m.vk.com/", "");
            vkVal = vkVal.replace("vk.com/", "");
            vkVal = vkVal.replace("Vk.com/", "");
            try {
                String vkJSON = vk.getUserInform(vkVal);
                JSONObject obj = new JSONObject(vkJSON);
                if (obj.has("response")) {
                    JSONArray arr = obj.getJSONArray("response");
                    JSONObject o = arr.getJSONObject(0);
                    //String idStr = getString("id");
                    String newVkStr = o.get("id") + "";
                    dbHelper.updateRecord(DBHelper.TABLE_SCOOLERS,
                            DBHelper.KEY_VK + "=" + DBHelper.toSQLString(inStr),
                            DBHelper.KEY_VK + "=" + DBHelper.toSQLString(newVkStr));
                    System.out.println(newVkStr);
                } else {
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
                JSONObject o = obj.getJSONObject("response");
                JSONArray arr = o.getJSONArray("items");
                for (Object jo : arr) {
                    if (!dbHelper.chechStudentRecordByVK((int) jo + "")) {
                        System.out.println("http://vk.com/id" + (int) jo);
                    }
                }
                //    System.out.println(arr);
            } else {
                System.out.println(req);
            }
            //dbHelper.chechStudentRecordByMail()
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void getAttemps() throws Exception {
        try (WebSelenium webSelenium = new WebSelenium()) {
            webSelenium.loginToMdl();
            Thread.sleep(1000);
            webSelenium.getAttempts("02_Ведение в Java");
        }
    }

    public static void copyQuestions() throws Exception {
        try (WebSelenium webSelenium = new WebSelenium()) {
            webSelenium.loginToMdl();
            Thread.sleep(1000);
            //webSelenium.copyQuestion("558,1");
            String arr[] = {
                    "122,1"
            };
            String arr2[] = {
                    "111,1",
                    "22,1"};

            System.out.println("yyeee");
            for (String a : arr) {
                webSelenium.copyQuestion(a, s -> true);
                Thread.sleep(100);
                webSelenium.moveCopiedQuestions(a, "1120,33", (n) -> n % 2 == 0);
                Thread.sleep(100);
            }
            for (String a : arr2) {
                webSelenium.copyQuestion(a, s -> true);
                Thread.sleep(100);
                webSelenium.moveCopiedQuestions(a, "1498,33", (n) -> n % 2 == 0);
                Thread.sleep(100);
            }

        }
    }

    public static void renameTests() throws Exception {
        try (WebSelenium webSelenium = new WebSelenium()) {
            webSelenium.loginToMdl();
            Thread.sleep(2000);
            String arr[][] = {
                    {"1510,33", "09_Практика_1"},
                    {"1521,33", "09_Практика_1_ДЗ"},
                    {"1511,33", "10_Практика_2"},
                    {"1522,33", "11_Практика_2_ДЗ"},
                    {"1512,33", "12_Практика_3"},
                    {"1523,33", "12_Практика_3_ДЗ"},
                    {"1513,33", "13_Практика_4"},
                    {"1524,33", "13_Практика_4_ДЗ"}
            };
            for (String[] a : arr) {
                webSelenium.renameTests(a[1], a[0]);
            }

        }
    }

    public ArrayList<ArrayList<String>> readAnichkovCSV() {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/source/AnichkovForm.csv")))) {
            String commandstring;
            ArrayList<ArrayList<String>> sList = new ArrayList<>();
            int cnt = 0;
            while ((commandstring = bufferedReader.readLine()) != null) {
                String ls_regex = "\".*?\"";
                Pattern pattern = Pattern.compile(ls_regex);
                Matcher matcher = pattern.matcher(commandstring);
                ArrayList<String> sl = new ArrayList<>();

                int i = 0;

                boolean flgAdd = false;
                while (matcher.find()) {
                    String tmp = matcher.group();
                    //System.out.println(tmp);
                    sl.add(tmp.replace("\"", ""));
                    i++;
                }
                cnt++;

                sList.add(sl);
            }
            return sList;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fuck");
        }
        System.out.println("eagasg");
        return null;
    }

    public static void addQuestionsToTest(){
        try (WebSelenium webSelenium = new WebSelenium()) {
            webSelenium.loginToMdl();
            Thread.sleep(1000);
            String sArr[][] = {
                    {"1522,33", "http://mdl.sch239.net/mod/quiz/view.php?id=1484"}
            };

            for (String [] sA:sArr) {
                webSelenium.addQuestionsToTest(sA[1], sA[0]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void addRefs() {
        try (WebSelenium webSelenium = new WebSelenium()) {
            webSelenium.loginToMdl();
            Thread.sleep(1000);
            String sArr[][] = {
                    {"1512,33", "11_Практика_3"},
                    {"1523,33", "11_Практика_3_ДЗ"},
                    {"1513,33", "12_Практика_4"},
                    {"1524,33", "12_Практика_4_ДЗ"}
            };

            for (String sa[] : sArr) {
                webSelenium.addRef(sa[0],sa[1]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fillRandom() {
        try (WebSelenium webSelenium = new WebSelenium()) {
            webSelenium.loginToMdl();
            Thread.sleep(1000);
            String sArr[] = {
                    "1510,33",
                    "1521,33",
                    "1511,33",
                    "1522,33",
                    "1512,33",
                    "1523,33",
                    "1513,33",
                    "1524,33"
            };

            for (String s : sArr) {
                webSelenium.fillRandom("1129,33", s, 13);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setTemplateToQuestions() throws Exception {
        try (WebSelenium webSelenium = new WebSelenium()) {
            webSelenium.loginToMdl();
            Thread.sleep(1000);
            String template = "{{ STUDENT_ANSWER | replace({'charAt':' WRONG_cahrat '}) }}";
            webSelenium.setTemplateToQuestions("1508,33", template, (s) -> !s.equals("07_Строки_"));

        }
    }

    public void fillAnichkov() {
        try (WebSelenium webSelenium = new WebSelenium()) {
            webSelenium.loginToAnichkov();

            ArrayList<ArrayList<String>> lst = readAnichkovCSV();
            lst.sort((o1, o2) -> o1.get(1).compareTo(o2.get(1)));
            //System.out.println( lst);
            String prevGroup = "0";
            if (lst != null) {
                //System.out.println(cnt);
                if (lst.size() > 0) {
                    int size1 = lst.size();
                    //String sArr[][] = new String[size1][size2];
                    for (ArrayList<String> l : lst) {
                        //sArr[i] = new String[size2];
                        if (!l.get(1).equals(prevGroup)) {
                            prevGroup = l.get(1);
                            webSelenium.goToSamsungPage(prevGroup);
                        }
                        l.remove(1);

                        String sArr[] = l.get(6).split("-");
                        l.set(6, sArr[2] + "." + sArr[1] + "." + sArr[0]);
                        for (int i = 0; i < l.size(); i++) {
                            if (l.get(i).replace(" ", "").equals("")) {
                                l.set(i, "-");
                            }
                        }
                        webSelenium.fillAnichkov(l);
                    }
                } else {
                    System.out.println("Пустой лист");
                }
            }

            System.out.println("Прочитано");

            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}


