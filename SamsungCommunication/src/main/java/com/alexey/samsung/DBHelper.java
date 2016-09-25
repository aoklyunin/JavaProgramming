package com.alexey.samsung;

import java.io.*;
import java.sql.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBHelper implements AutoCloseable {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_NAME = "Students";
    static final String DB_URL = "jdbc:mysql://localhost/";

    private static final String KEY_VAL = "value";
    private static final String KEY_CKEY = "ckey";

    public static final String TABLE_SCOOLERS = "schoolers";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_MAIL = "mail";
    private static final String KEY_VK = "vk";
    private static final String KEY_GITHUB = "github";
    private static final String KEY_M_LOGIN = "mlogin";
    private static final String KEY_M_PASSWORD = "mpassword";
    private static final String KEY_TEL = "tel";
    private static final String KEY_ADDITIONAL = "additional";
    private static final String KEY_PROJECT = "project";


    static final String USER = "root";
    static final String PASS = "toor";

    static final String confTable = "CONF_TABLE";

    Connection conn = null;
    Statement stmt = null;

    private void query(String sql) throws SQLException {
        stmt.executeUpdate(sql);
    }

    public void createDB(String dbName) throws SQLException {
        query("CREATE DATABASE " + dbName);
    }

    public void connect() throws SQLException, ClassNotFoundException {
        // загружаем класс
        Class.forName(JDBC_DRIVER);
        // подклюаемся к базе
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        System.out.println("Подключились к базе");
        stmt = conn.createStatement();
        query("USE " + DB_NAME + ";");
    }

    public void createTable(String tableName, Map<String, String> m) throws SQLException {
        String q = "CREATE TABLE " + tableName +
                "(id INTEGER not NULL, ";
        for (Map.Entry<String, String> e : m.entrySet()) {
            q += e.getKey() + " " + e.getValue() + ", ";
        }
        q += " PRIMARY KEY ( id ))";
        query(q);
        System.out.println("Создали таблицу");
    }

    // генерация из массива строк sql записей
    private String getQueryValues(String[][] values) {
        String q = " VALUES ";
        for (String[] sArr : values) {
            q += "(";
            for (String key : sArr
                    ) {
                q += key + ", ";
            }
            q = q.substring(0, q.length() - 2);
            q += "), ";
        }
        q = q.substring(0, q.length() - 2);
        q += ";";
        return q;
    }

    // добавить одну запись с явным указанием ключей через словарь
    public void addRecord(String tableName, Map<String, String> m) throws SQLException {
        String[][] sArr = new String[1][m.size()];
        String[] keys = new String[m.size()];
        int i = 0;
        for (Map.Entry<String, String> e : m.entrySet()) {
            sArr[0][i] = e.getValue();
            keys[i] = e.getKey();
        }
        addRecords(tableName, keys, sArr);
        System.out.println("Значения добавлены");
    }

    // добавить одну запись с явным указанием ключей через массивы
    private void addRecord(String tableName, String[] keys, String[] values) throws SQLException {
        String[][] sArr = new String[1][];
        sArr[0] = values;
        addRecords(tableName, keys, sArr);
    }

    // добавить записи с явным указанием ключей
    private void addRecords(String tableName, String[] keys, String[][] values) throws SQLException {
        String q = "INSERT INTO " + tableName + "(";
        for (String s : keys) {
            q += s + ", ";
        }
        q = q.substring(0, q.length() - 2);
        q += ")" + getQueryValues(values);
        System.out.println(q);
        query(q);
    }

    // добавить записи без указания ключей
    private void addRecords(String tableName, String[][] values) throws SQLException {
        String q = "INSERT INTO " + tableName + " " + getQueryValues(values);
        //System.out.println(q);
        query(q);
    }

    // добавить запись без указания ключей
    public void addRecord(String tableName, String[] values) throws SQLException {
        String[][] sArr = new String[1][];
        sArr[0] = values;
        addRecords(tableName, sArr);
    }

    // получаем все записи из таблицы конфигураций
    public void getAllConf() throws SQLException {
        String query = "SELECT * FROM " + confTable;
        ResultSet rs = stmt.executeQuery(query);
        boolean flgNotFound = true;
        while (rs.next()) {
            flgNotFound = false;
            //String coffeeName = rs.getString("COF_NAME");
            System.out.println(rs.getString(KEY_VAL));
        }
        if (flgNotFound) System.out.println("Не найдено ни одной записи");
    }

    // получить запись по условию
    private ResultSet getRecord(String condition) throws SQLException {
        String query = "SELECT * FROM " + confTable + " WHERE " + condition;
        ResultSet rs = stmt.executeQuery(query);
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }

    public void parceCsv()throws SQLException   {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/source/FirstForm.csv")))){
            String commandstring;
            ArrayList<ArrayList<String>> sList = new ArrayList<>();
            while ((commandstring = bufferedReader.readLine()) != null) {
                String ls_regex = "\".*?\"";
                Pattern pattern = Pattern.compile(ls_regex);
                Matcher matcher = pattern.matcher(commandstring);
                ArrayList<String> sl = new ArrayList<>();
                sList.add(sl);
                while (matcher.find()) {
                    String tmp = matcher.group();
                    sl.add(tmp);
                }
            }
            int size1 = sList.size();
            int size2 = sList.get(1).size()-1;
            String sArr[][] = new String[size1][size2];
            for (int i = 0; i < size1; i++) {
                sArr[i]=new String[size2];
                for (int j = 0; j < size2; j++) {
                    sArr[i][j] = sList.get(i).get(j+1);
                }
            }

            String kArr[] ={
                KEY_GITHUB, KEY_M_LOGIN, KEY_M_PASSWORD, KEY_NAME, KEY_MAIL,KEY_VK,KEY_TEL
            };
            addRecords(TABLE_SCOOLERS,kArr,sArr);
            for (int i = 0; i <size1 ; i++) {
                for (int j = 0; j <size2 ; j++) {
                    System.out.print(sArr[i][j]+" ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fuck");
        }
        System.out.println("eagasg");
        // Questions.generateVariants();
    }


    // записать значение в базу
    public void setConfVal(String key, String val) throws SQLException {
        if (getConfVal(key) == null) {
            String[] keys = {KEY_CKEY, KEY_VAL};
            String[] sArr = {toSQLString(key), toSQLString(val)};
            addRecord(confTable, keys, sArr);
        } else {
            System.out.println("Запись уже есть");
            updateRecord(confTable, KEY_CKEY + "=" + toSQLString(key), KEY_VAL + "=" + toSQLString(val));
        }
    }

    // обновить запись в таблице по условию
    private void updateRecord(String table, String condition, String operation) throws SQLException {
        String q = "UPDATE " + table + " SET " + operation + " WHERE " + condition;
        query(q);
    }

    // получить значение из конфигурационной таблицы
    String getConfVal(String Key) throws SQLException {
        ResultSet rs = getRecord("ckey=" + toSQLString(Key));
        if (rs != null)    //String coffeeName = rs.getString("COF_NAME");
            return rs.getString(KEY_VAL);
        else
            return null;
    }

    // переаод в  SQL строку
    private static String toSQLString(String s) {
        return "\'" + s + "\'";
    }

    @Override
    public void close() throws Exception {
        //finally block used to close resources
        try {
            if (stmt != null)
                conn.close();
        } catch (SQLException ignored) {
        }// do nothing
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException ignored) {
        }//end finally try
    }
}
