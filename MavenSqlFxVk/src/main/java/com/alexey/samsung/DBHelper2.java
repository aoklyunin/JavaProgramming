package com.alexey.samsung;

import java.sql.*;

/**
 * Created by aokly on 25.09.2016.
 */
public class DBHelper2 {
    Connection crunchifyConn = null;
    PreparedStatement crunchifyPrepareStat = null;

    public void test() {

        try {
            log("-------- Simple Crunchify Tutorial on how to make JDBC connection to MySQL DB locally on macOS ------------");
            makeJDBCConnection();
/*
            log("\n---------- Adding company 'Crunchify LLC' to DB ----------");
            addDataToDB("Crunchify, LLC.", "NYC, US", 5, "http://crunchify.com");
            addDataToDB("Google Inc.", "Mountain View, CA, US", 50000, "https://google.com");
            addDataToDB("Apple Inc.", "Cupertino, CA, US", 30000, "http://apple.com");

            log("\n---------- Let's get Data from DB ----------");
            getDataFromDB();
*/
            crunchifyPrepareStat.close();
            crunchifyConn.close(); // connection close

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    private void makeJDBCConnection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            log("Congrats - Seems your MySQL JDBC Driver Registered!");
        } catch (ClassNotFoundException e) {
            log("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
            e.printStackTrace();
            return;
        }

        try {
            // DriverManager: The basic service for managing a set of JDBC drivers.
            crunchifyConn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "toor");
            if (crunchifyConn != null) {
                log("Connection Successful! Enjoy. Now it's time to push data");
            } else {
                log("Failed to make connection!");
            }
        } catch (SQLException e) {
            log("MySQL Connection Failed!");
            e.printStackTrace();
            return;
        }

    }

    private void addDataToDB(String companyName, String address, int totalEmployee, String webSite) {

        try {
            String insertQueryStatement = "INSERT  INTO  Employee  VALUES  (?,?,?,?)";

            crunchifyPrepareStat = crunchifyConn.prepareStatement(insertQueryStatement);
            crunchifyPrepareStat.setString(1, companyName);
            crunchifyPrepareStat.setString(2, address);
            crunchifyPrepareStat.setInt(3, totalEmployee);
            crunchifyPrepareStat.setString(4, webSite);

            // execute insert SQL statement
            crunchifyPrepareStat.executeUpdate();
            log(companyName + " added successfully");
        } catch (

                SQLException e) {
            e.printStackTrace();
        }
    }

    private void getDataFromDB() {

        try {
            // MySQL Select Query Tutorial
            String getQueryStatement = "SELECT * FROM employee";

            crunchifyPrepareStat = crunchifyConn.prepareStatement(getQueryStatement);

            // Execute the Query, and get a java ResultSet
            ResultSet rs = crunchifyPrepareStat.executeQuery();

            // Let's iterate through the java ResultSet
            while (rs.next()) {
                String name = rs.getString("Name");
                String address = rs.getString("Address");
                int employeeCount = rs.getInt("EmployeeCount");
                String website = rs.getString("Website");

                // Simply Print the results
                System.out.format("%s, %s, %s, %s\n", name, address, employeeCount, website);
            }

        } catch (

                SQLException e) {
            e.printStackTrace();
        }

    }

    // Simple log utility
    private static void log(String string) {
        System.out.println(string);
    }
}
