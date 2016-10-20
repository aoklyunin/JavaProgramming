package com.alexey.samsung;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.swing.text.DateFormatter;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBHelper implements AutoCloseable {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_NAME = "students";
    static final String DB_URL = "jdbc:mysql://localhost/";

    private static final String KEY_VAL = "value";
    private static final String KEY_CKEY = "ckey";

    public static final String TABLE_SCOOLERS = "schoolers";
    public static final String TABLE_INFORMATIC = "informatic";

    static final String KEY_ID = "id";
    static final String KEY_NAME = "name";
    static final String KEY_MAIL = "mail";
    static final String KEY_VK = "vk";
    static final String KEY_GITHUB = "github";
    static final String KEY_M_LOGIN = "mlogin";
    static final String KEY_M_PASSWORD = "mpassword";
    static final String KEY_TEL = "tel";
    static final String KEY_ADDITIONAL = "additional";
    static final String KEY_PROJECT = "project";
    static final String KEY_GROUP = "groupNumber";
    static final String KEY_ATTEMPT_TYPE = "a_type";
    static final String KEY_STATE = "state";
    static final String KEY_STARTS = "starts";
    static final String KEY_ENDS = "ends";
    static final String KEY_TM = "tm";
    static final String KEY_EVAULATION = "evaulation";
    static final String KEY_HREF = "href";
    static final String KEY_SUM = "sum";
    static final String KEY_TEST_NAME = "test_name";
    static final String KEY_TEST_TYPE = "t_type";
    static final String KEY_WORK_TYPE = "w_type";
    static final String KEY_DATE = "date";
    static final String KEY_EST_1 = "est1";
    static final String KEY_EST_2 = "est2";
    static final String KEY_EST_3 = "est3";
    static final String KEY_EST_4 = "est4";
    static final String KEY_EST_5 = "est5";


    static final String USER = "root";
    static final String PASS = "toor";

    static final String confTable = "CONF_TABLE";
    static final String attemptTable = "attempts";
    public static final String taskTable = "tasks";

    Connection conn = null;
    Statement stmt = null;

    private void query(String sql) throws SQLException {
        //System.out.println(sql);
        stmt.executeUpdate(sql);
    }

    public void createDB(String dbName) throws SQLException {
        query("CREATE DATABASE " + dbName);
    }

    public ArrayList<ArrayList<String>> getValsRegister() throws SQLException {
        String query = "SELECT * FROM " + TABLE_SCOOLERS;
        ResultSet rs = stmt.executeQuery(query);
        //String login,String password, String name, String mail
        ArrayList<ArrayList<String>> lst = new ArrayList<>();
        while (rs.next()) {
            ArrayList<String> ls = new ArrayList<>();
            lst.add(ls);
            ls.add(rs.getString(KEY_M_LOGIN));
            ls.add(rs.getString(KEY_M_PASSWORD));
            ls.add(rs.getString(KEY_NAME));
            ls.add(rs.getString(KEY_MAIL));
        }
        return lst;
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

    // генерация из массива строк sql записей
    private String getQueryValues(String[] values) {
        String q = " VALUES ";
        q += "(";
        for (String key : values
                ) {
            q += key + ", ";
        }
        q = q.substring(0, q.length() - 2);
        q += "), ";
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

    // добавить одну запись с явным указанием ключей через массивы
    public void addAttemptRecord(String tableName, String[] values, String condition) throws SQLException {
        String q = "INSERT INTO " + tableName + " SELECT * FROM (SELECT ";
        for (int i = 0; i < 5; i++) {
            q += values[i] + ", ";
        }
        q += values[5] + " AS starttime" + ", ";
        q += values[6] + " AS endtime" + ", ";
        q += values[7] + ", ";
        q += values[8] + " AS evaluation" + ", ";
        q += values[9] + ", ";
        q += values[10] + " AS sum" + ", ";
        q += values[11] + ", ";
        q += values[12];
        q += ") AS tmp WHERE NOT EXISTS ( SELECT id FROM " + tableName + " WHERE " + condition + ") LIMIT 1";

        System.out.println(q);
        query(q);
    }


    // добавить записи с явным указанием ключей
    public void addRecords(String tableName, String[] keys, String[][] values) throws SQLException {
        String q = "INSERT INTO " + tableName + " (";
        for (String s : keys) {
            q += s + ", ";
        }
        q = q.substring(0, q.length() - 2);
        q += ")" + getQueryValues(values);
        System.out.println(q);
        query(q);
    }

    // добавить записи без указания ключей
    public void addRecords(String tableName, String[][] values) throws SQLException {
        String q = "INSERT INTO " + tableName + " " + getQueryValues(values);
        System.out.println(q);
        query(q);
    }

    // добавить записи без указания ключей
    public void addRecords(String tableName, String[][] values, String condition) throws SQLException {
        String q = "INSERT INTO " + tableName + " " + getQueryValues(values);
        System.out.println(q);
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
    private ResultSet getRecord(String condition, String tableName) throws SQLException {
        String q = "SELECT * FROM " + tableName + " WHERE " + condition;
        //System.out.println(q);
        ResultSet rs = stmt.executeQuery(q);
        if (rs.next()) {
            return rs;
        } else {
            return null;
        }
    }


    public ArrayList<HashMap<String, String>> getStudentRecs() throws SQLException {
        String q = "SELECT * FROM " + TABLE_SCOOLERS;
        //System.out.println(q);
        ResultSet rs = stmt.executeQuery(q);
        ArrayList<HashMap<String, String>> lst = new ArrayList<>();
        while (rs.next()) {
            HashMap<String, String> hm = new HashMap<>();
            lst.add(hm);
            hm.put(KEY_GITHUB, rs.getString(KEY_GITHUB));
            hm.put(KEY_M_LOGIN, rs.getString(KEY_M_LOGIN));
            hm.put(KEY_M_PASSWORD, rs.getString(KEY_M_PASSWORD));
            hm.put(KEY_NAME, rs.getString(KEY_NAME));
            hm.put(KEY_VK, rs.getString(KEY_VK));
            hm.put(KEY_TEL, rs.getString(KEY_TEL));
        }
        return lst;
    }

    public boolean chechStudentRecordByMail(String mail) throws SQLException {
        return (getRecord("mail=" + toSQLString(mail), TABLE_SCOOLERS) != null);
    }

    public boolean chechStudentRecordByVK(String mail) throws SQLException {
        return (getRecord("vk=" + toSQLString(mail), TABLE_SCOOLERS) != null);
    }

    public void checkMails() throws SQLException {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/source/mails.txt")))) {
            String commandstring;
            while ((commandstring = bufferedReader.readLine()) != null) {
                if (chechStudentRecordByMail(commandstring)) {
                    System.out.println(commandstring);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // просматриваем список всех адресов почты, и на те, которых в базе нет
    // отправляется предупредительное письмо
    public void parceMailList() throws Exception {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/source/mails.txt")))) {
            String commandstring;
            ArrayList<ArrayList<String>> sList = new ArrayList<>();
            GMailSender sender = new GMailSender("aoklyunin@gmail.com", "aoklyunin1990");
            String subject = "Обучение в IT школе Samsung";
            String body = "Уважаемый Учащийся,\n\n" +
                    "Вы до сих пор не заполнили гугл-форму. Если до конца недели Вы" +
                    " не заполните её, я буду вынужден отчислить Вас, т.к. из-за незаполненной формы Вы " +
                    "не можете выполнять задания.\n\n" +
                    "С уважением, \n Алексей Клюнин";
            while ((commandstring = bufferedReader.readLine()) != null) {
                try {
                    if (!chechStudentRecordByMail(commandstring.replace(" ", ""))) {

                        sender.sendMail(subject,
                                body,
                                "aoklyunin@gmail.com",
                                commandstring);
                        System.out.println(commandstring);
                    }
                } catch (SQLException e) {
                    System.out.println("SQL Ошибка " + e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fuck");
        }
        System.out.println("eagasg");
        // Questions.generateVariants();
    }

    public void parceCsv() throws SQLException {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/source/form.csv")))) {
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
                    sl.add(tmp);
                    if (i == 5) {
                        //System.out.println(tmp);
                        try {
                            if (!chechStudentRecordByMail(tmp.replace("\"", "").replace(" ", ""))) {
                                System.out.println("Не найдено");
                                flgAdd = true;
                            }
                        } catch (SQLException e) {
                            System.out.println("SQL Ошибка " + e);
                        }
                    }
                    i++;

                }
                cnt++;
                if (flgAdd) {
                    sList.add(sl);
                }
            }
            //System.out.println(cnt);
            if (sList.size() > 0) {
                int size1 = sList.size();
                int size2 = sList.get(0).size() - 1;
                String sArr[][] = new String[size1][size2];
                for (int i = 0; i < size1; i++) {
                    sArr[i] = new String[size2];
                    for (int j = 0; j < size2; j++) {
                        sArr[i][j] = sList.get(i).get(j + 1).replace(" ", "");
                    }
                }

                String kArr[] = {
                        KEY_GITHUB, KEY_M_LOGIN, KEY_M_PASSWORD, KEY_NAME, KEY_MAIL, KEY_VK, KEY_TEL
                };
                addRecords(TABLE_SCOOLERS, kArr, sArr);
                for (int i = 0; i < size1; i++) {
                    for (int j = 0; j < size2; j++) {
                        System.out.print(sArr[i][j] + " ");
                    }
                    System.out.println();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fuck");
        }
        System.out.println("eagasg");
        // Questions.generateVariants();
    }

    class InformaticStudent {
        int group;
        String name;

        InformaticStudent(String s) {
            String[] sArr = s.split(" ");
            group = Integer.parseInt(sArr[0]);
            name = sArr[1] + " " + sArr[2];
        }

    }

    public static String toSQLDTString(DateTime dt) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-d HH:mm:ss");
        return "\'" + fmt.print(dt) + "\'";
    }

    public static String toSQLDString(Date dt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        return "\'" + dateFormat.format(dt) + "\'";
    }


    public Attempt getAFromResultSet(ResultSet rs) throws SQLException {
        Attempt at = new Attempt(
                rs.getString(KEY_MAIL),
                rs.getString(KEY_STATE),
                rs.getDate(KEY_STARTS) + "",
                rs.getDate(KEY_ENDS) + "",
                rs.getString(KEY_TM),
                rs.getFloat(KEY_EVAULATION) + "",
                rs.getString(KEY_HREF),
                rs.getFloat(KEY_SUM),
                rs.getString(KEY_NAME));
        return at;
    }

    public Task getTaskFromResultSet(ResultSet rs) throws SQLException {
        String sArr[] = {
                rs.getString(KEY_EST_1),
                rs.getString(KEY_EST_2),
                rs.getString(KEY_EST_3),
                rs.getString(KEY_EST_4),
                rs.getString(KEY_EST_5)
        };
        Task at = new Task(
                rs.getInt(KEY_TEST_TYPE),
                rs.getInt(KEY_WORK_TYPE),
                rs.getDate(KEY_DATE),
                sArr,
                rs.getString(KEY_NAME)
                );
        return at;
    }


    public ArrayList<Task> getTasks(Date start, Date end) throws SQLException {
        ArrayList<Task> aLst = new ArrayList<>();
        String query = "SELECT * FROM " + taskTable + " WHERE " +
                "date >= " + toSQLDString(start) + " AND date <= " + toSQLDString(end);
        ResultSet rs = stmt.executeQuery(query);
        //String login,String password, String name, String mail
        while (rs.next()) {
            aLst.add(getTaskFromResultSet(rs));
        }
        return aLst;
    }


    public ArrayList<String> getSchoolers(int group) throws SQLException {
        ArrayList<String> aLst = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_INFORMATIC +" WHERE ";
        String condition  = "";
        switch (group){
            case 0:
                condition = "FALSE";
                break;
            case 1:
            case 2:
                condition = KEY_GROUP+"="+group;
                break;
            case 3:
                condition = KEY_GROUP+"=1 OR "+KEY_GROUP+"=2";
                break;
        }
        query += condition;
        ResultSet rs = stmt.executeQuery(query);
        //String login,String password, String name, String mail
        while (rs.next()) {
            aLst.add(CustomOperations.reverseName(rs.getString(KEY_NAME)));
        }
        return aLst;
    }



    public ArrayList<Attempt> getAttempts(ArrayList<Task> lst, ArrayList<String> scoolerList) throws SQLException {
        ArrayList<Attempt> aLst = new ArrayList<>();
        if (scoolerList.size()==0)return  aLst;
        String query = "SELECT * FROM " + attemptTable +" WHERE (";
        for (Task task:lst){
            query += KEY_TEST_NAME + "="+toSQLString(task.name)+" OR ";
        }
        query = query.substring(0,query.length()-4)+")AND(";
        for (String name:scoolerList){
            query += KEY_NAME + "="+toSQLString(name)+" OR ";
        }
        query = query.substring(0,query.length()-4)+")";
        //System.out.println(query.length());
        //System.out.println(query);
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            aLst.add(getAFromResultSet(rs));
        }
        return aLst;
    }

    public void loadInfromaticFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/source/informatic.txt")))) {
            String commandstring;
            ArrayList<InformaticStudent> iLst = new ArrayList<>();
            while ((commandstring = bufferedReader.readLine()) != null) {
                iLst.add(new InformaticStudent(commandstring));
            }
            String[][] sArr = new String[iLst.size()][3];
            for (int i = 0; i < iLst.size(); i++) {
                sArr[i][0] = "NULL";
                sArr[i][1] = toSQLString(iLst.get(i).name);
                sArr[i][2] = iLst.get(i).group + "";
            }
            String kArr[] = {
                    KEY_ID, KEY_NAME, KEY_GROUP
            };

            addRecords(TABLE_INFORMATIC, kArr, sArr);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fuck");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String[]> getInformaticSchooler() throws SQLException {
        String query = "SELECT * FROM " + TABLE_INFORMATIC;
        ResultSet rs = stmt.executeQuery(query);
        //String login,String password, String name, String mail
        ArrayList<String[]> lst = new ArrayList<>();
        while (rs.next()) {
            String[] sArr = new String[2];
            lst.add(sArr);
            sArr[0] = rs.getString(KEY_NAME);
            sArr[1] = rs.getInt(KEY_ID) + "";
        }
        return lst;
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
    void updateRecord(String table, String condition, String operation) throws SQLException {
        String q = "UPDATE " + table + " SET " + operation + " WHERE " + condition;
        query(q);
    }

    // получить значение из конфигурационной таблицы
    String getConfVal(String Key) throws SQLException {
        ResultSet rs = getRecord("ckey=" + toSQLString(Key), confTable);
        if (rs != null)    //String coffeeName = rs.getString("COF_NAME");
            return rs.getString(KEY_VAL);
        else
            return null;
    }

    // переаод в  SQL строку
    public static String toSQLString(String s) {
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
