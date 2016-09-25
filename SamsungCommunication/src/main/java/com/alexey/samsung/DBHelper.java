package com.alexey.samsung;

import java.sql.*;
import java.util.Map;

/**
 * Created by aokly on 24.09.2016.
 */
public class DBHelper implements AutoCloseable {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL_OLD = "jdbc:mysql://localhost/";
    static final String DB_URL = "jdbc:mysql:myDB.db";

    public static final String KEY_VAL = "val";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "toor";

    static final String confTable = "CONF_TABLE";

    Connection conn = null;
    Statement stmt = null;

    public static final String DB_NAME = "Students";

    public void query(String sql) throws SQLException {
        stmt.executeUpdate(sql);
    }

    public void createDB(String dbName) throws SQLException {
        query("CREATE DATABASE " + dbName);
    }

    public void connect() throws SQLException, ClassNotFoundException {
        // загружаем класс
        Class.forName("com.mysql.jdbc.Driver");
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
        System.out.println(q);
        query(q);
        System.out.println("Создали таблицу");
    }
    // генерация из массива строк sql записей
    private String getQueryValues(String [][]values){
        String q=" VALUES ";
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
    public void addRecord(String tableName, String[] keys, String[] values) throws SQLException {
        String[][] sArr = new String[1][];
        sArr[0] = values;
        addRecords(tableName, keys, sArr);
    }
    // добавить записи с явным указанием ключей 
    public void addRecords(String tableName, String[] keys, String[][] values) throws SQLException {
        String q = "INSERT INTO " + tableName + "(";
        for (String s : keys) {
            q += s + ", ";
        }
        q = q.substring(0, q.length() - 2);
        q += ")"+getQueryValues(values);
        System.out.println(q);
        query(q);
    }
    // добавить записи без указания ключей
    public void addRecords(String tableName, String[][] values) throws SQLException {
        String q = "INSERT INTO " + tableName + " "+getQueryValues(values);
        //System.out.println(q);
        query(q);
    }
    // добавить запись без указания ключей
    public void addRecord(String tableName, String[] values) throws SQLException {
        String[][] sArr = new String[1][];
        sArr[0] = values;
        addRecords(tableName, sArr);
    }

    public void getAllConf() throws SQLException {
        String query = "SELECT * FROM " + confTable;
        ResultSet rs = stmt.executeQuery(query);
        if (!rs.next())
            System.out.println("Не найдено записей");
        while (rs.next()) {
            //String coffeeName = rs.getString("COF_NAME");
            System.out.println(rs.getString(KEY_VAL));
        }
    }
    public static String toSQLString(String s){
        return "\""+s+"\"";
    }

    
    public void createConfTable(Map<String, String> m) throws SQLException {
        createTable(confTable, m);
    }

    @Override
    public void close() throws Exception {
        //finally block used to close resources
        try {
            if (stmt != null)
                conn.close();
        } catch (SQLException se) {
        }// do nothing
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }//end finally try
    }
}
