package com.alexey.samsung;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;


public class Main extends Application {

    private Stage primaryStage;
    private AnchorPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //vkHelper.save("303154598",0,1);
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRootLayout();
        VkApi vk = new VkApi();
        //vk.connect( );
    }


    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/hello.fxml"));
            rootLayout = (AnchorPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            //primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

