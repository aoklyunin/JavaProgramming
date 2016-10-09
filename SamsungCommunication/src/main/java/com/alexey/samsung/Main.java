package com.alexey.samsung;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

// TODO: 25.09.2016  Нужно кнопку VK скрыть и открывать только когда подключение сделано
// http://stackoverflow.com/questions/30308065/changing-the-text-of-a-label-from-a-different-class-in-javafx
// подключить прокси к selenium

public class Main extends Application {
    Logger logger;
    PrintStream console = System.err;
    private void initErrLog() throws FileNotFoundException {
        File file = new File("err.log");
        FileOutputStream fos = new FileOutputStream(file);
        PrintStream ps = new PrintStream(fos);
        System.setErr(ps);
    }
    private Stage primaryStage;
    private AnchorPane rootLayout;
    public VkApi vk;
    @Override
    public void start(Stage primaryStage){
       /* try {
            initErrLog();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

        //vkHelper.save("303154598",0,1);
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SamsungCommunication");

        //CustomOperations.getUnregistredYsersFromConf();
        initRootLayout();
        //DBHelper dbHelper= new DBHelper();
        //dbHelper.connect();
        //dbHelper.parceCsv();

        //dbHelper.parceMailList();
      //  dbHelper.parceCsv();
       // CustomOperations customOperations = new CustomOperations();
       // customOperations.fillAnichkov();
        try {
            CustomOperations.renameTests();
        } catch (Exception e) {
            e.printStackTrace();
        }
/*
        try(WebSelenium webSelenium = new WebSelenium("203.223.143.51:8080")) {
            System.out.println("load2");
            System.out.println(webSelenium.getIpFromPage());
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }

        try(WebSelenium webSelenium = new WebSelenium()) {
            System.out.println("load1");
            System.out.println(webSelenium.getIpFromPage());
        }catch (Exception e){
            e.printStackTrace();
        }

        try(WebSelenium webSelenium = new WebSelenium("77.123.18.56:81")) {
            System.out.println("load3");
            System.out.println(webSelenium.getIpFromPage());
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }*/

        //System.out.println(WebSelenium.loadCurPageHTTP("http://google.com"));
       /* GMailSender sender = new GMailSender("aoklyunin@gmail.com", "aoklyunin1990");
        sender.sendMail("Тестовое письмо",
                "Тестовое письмо\nВнезапно мдааааа",
                "aoklyunin@gmail.com",
                "aoklyunin@gmail.com");

        System.out.println("Completed");*/
        //vk = new VkApi();
        primaryStage.close();
    }


    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/hello.fxml"));
            rootLayout = (AnchorPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

