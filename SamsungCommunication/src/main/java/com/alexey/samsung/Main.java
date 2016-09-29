package com.alexey.samsung;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

// TODO: 25.09.2016  Нужно кнопку VK скрыть и открывать только когда подключение сделано
// http://stackoverflow.com/questions/30308065/changing-the-text-of-a-label-from-a-different-class-in-javafx

public class Main extends Application {

    private Stage primaryStage;
    private AnchorPane rootLayout;
    public VkApi vk;
    @Override
    public void start(Stage primaryStage) throws Exception{
        //vkHelper.save("303154598",0,1);
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SamsungCommunication");

        CustomOperations.getUnregistredYsersFromConf();
        initRootLayout();
        //DBHelper dbHelper= new DBHelper();
        //dbHelper.connect();
        //dbHelper.parceMailList();
      //  dbHelper.parceCsv();
        //WebSelenium.test();
       /* GMailSender sender = new GMailSender("aoklyunin@gmail.com", "aoklyunin1990");
        sender.sendMail("Тестовое письмо",
                "Тестовое письмо\nВнезапно мдааааа",
                "aoklyunin@gmail.com",
                "aoklyunin@gmail.com");

        System.out.println("Completed");*/
        //vk = new VkApi();

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

